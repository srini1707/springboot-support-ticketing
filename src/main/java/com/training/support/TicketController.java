package com.training.support;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/v1/tickets")
public class TicketController {
 private final TicketService s;public TicketController(TicketService s){this.s=s;}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public Ticket create(@Valid @RequestBody TicketRequest r){return s.create(r);}
 @GetMapping("/{id}") public Ticket get(@PathVariable long id){return s.get(id);}
 @GetMapping public List<Ticket> list(@RequestParam String requesterId){return s.byRequester(requesterId);}
 @PatchMapping("/{id}/assign") public Ticket assign(@PathVariable long id,@RequestParam String agentId){return s.assign(id,agentId);}
 @PatchMapping("/{id}/status") public Ticket status(@PathVariable long id,@RequestParam String target){return s.transition(id,target);}
 @PostMapping("/{id}/comments") @ResponseStatus(HttpStatus.CREATED) public TicketComment comment(@PathVariable long id,@Valid @RequestBody CommentRequest r){return s.addComment(id,r);}
 @GetMapping("/{id}/comments") public List<TicketComment> comments(@PathVariable long id){return s.comments(id);}
}
