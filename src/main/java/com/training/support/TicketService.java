package com.training.support;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
@Service
public class TicketService {
 private final Map<Long,Ticket> tickets=new ConcurrentHashMap<>();private final Map<Long,List<TicketComment>> comments=new ConcurrentHashMap<>();
 private final AtomicLong ids=new AtomicLong(),commentIds=new AtomicLong();
 public Ticket create(TicketRequest r){var now=Instant.now();var t=new Ticket(ids.incrementAndGet(),r.requesterId(),r.subject(),r.description(),r.priority().toUpperCase(),"OPEN",null,now,now);tickets.put(t.id(),t);comments.put(t.id(),Collections.synchronizedList(new ArrayList<>()));return t;}
 public Ticket get(long id){var t=tickets.get(id);if(t==null)throw new NoSuchElementException("Ticket not found");return t;}
 public List<Ticket> byRequester(String requester){return tickets.values().stream().filter(t->t.requesterId().equals(requester)).sorted(Comparator.comparing(Ticket::createdAt).reversed()).toList();}
 public Ticket assign(long id,String agent){var t=get(id);if(t.status().equals("CLOSED"))throw new IllegalStateException("Closed ticket cannot be assigned");return save(t,t.status(),agent);}
 public Ticket transition(long id,String target){var t=get(id);var allowed=Map.of("OPEN",List.of("IN_PROGRESS","CLOSED"),"IN_PROGRESS",List.of("WAITING_ON_CUSTOMER","RESOLVED","CLOSED"),"WAITING_ON_CUSTOMER",List.of("IN_PROGRESS","CLOSED"),"RESOLVED",List.of("CLOSED","IN_PROGRESS"),"CLOSED",List.<String>of());
  target=target.toUpperCase();if(!allowed.getOrDefault(t.status(),List.of()).contains(target))throw new IllegalStateException("Invalid ticket transition "+t.status()+" -> "+target);return save(t,target,t.assignedAgentId());}
 private Ticket save(Ticket t,String status,String agent){var n=new Ticket(t.id(),t.requesterId(),t.subject(),t.description(),t.priority(),status,agent,t.createdAt(),Instant.now());tickets.put(t.id(),n);return n;}
 public TicketComment addComment(long id,CommentRequest r){get(id);var c=new TicketComment(commentIds.incrementAndGet(),id,r.authorId(),r.message(),Instant.now());comments.get(id).add(c);return c;}
 public List<TicketComment> comments(long id){get(id);return List.copyOf(comments.get(id));}
}
