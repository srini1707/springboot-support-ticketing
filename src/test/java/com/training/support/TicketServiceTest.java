package com.training.support;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class TicketServiceTest {
 @Test void createsAssignsAndComments(){var s=new TicketService();var t=s.create(new TicketRequest("R1","Login failure","Cannot sign in","HIGH"));
  assertEquals("OPEN",t.status());assertEquals("AGENT-1",s.assign(t.id(),"AGENT-1").assignedAgentId());
  s.addComment(t.id(),new CommentRequest("AGENT-1","Investigating"));assertEquals(1,s.comments(t.id()).size());}
}
