package nts.uk.ctx.at.schedule.ws.schedule.alarm.workmethodrelationship;

import nts.uk.ctx.at.schedule.app.command.schedule.alarm.workmethodrelationship.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("work/method/relationship")
@Produces(MediaType.APPLICATION_JSON)
public class WorkMethodRelationshipOrgWS {

    @Inject
    private RigisterWorkingRelationshipCommandHandler rigisterWorkingRelationshipCommandHandler;

    @Inject
    private ChangeWorkingRelationshipCommandHandler changeWorkingRelationshipCommandHandler;

    @Inject
    private DeleteWorkMethodOrgCommandHandler deleteWorkMethodOrgCommandHandler;

    @POST
    @Path("delete")
    public void deleteWorkingRelationship(DeleteWorkMethodOrgCommand command){

        deleteWorkMethodOrgCommandHandler.handle(command);
    }

    @POST
    @Path("register")
    public void registerWorkingRelationship(RigisterWorkingRelationshipCommand command){

        rigisterWorkingRelationshipCommandHandler.handle(command);
    }

    @POST
    @Path("update")
    public void updateWorkingRelationship(ChangeWorkingRelationshipCommand command){

        changeWorkingRelationshipCommandHandler.handle(command);
    }

}
