package nts.uk.ctx.at.schedule.ws.schedule.alarm.workmethodrelationship;

import nts.uk.ctx.at.schedule.app.command.schedule.alarm.workmethodrelationship.company.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("work/method/company")
@Produces(MediaType.APPLICATION_JSON)
public class WorkMethodRelationshipCmpWS {

    @Inject
    private RigisterWorkingRelationshipCmpCommandHandler rigisterWorkingRelationshipCmpCommandHandler;

    @Inject
    private UpdateWorkingRelationshipCmpCommandHandler updateWorkingRelationshipCmpCommandHandler;

    @Inject
    private DeleteWorkMethodCmpCommandHandler deleteWorkMethodCmpCommandHandler;

    @POST
    @Path("delete")
    public void deleteWorkingRelationship(DeleteWorkMethodCmpCommand command){

        deleteWorkMethodCmpCommandHandler.handle(command);
    }

    @POST
    @Path("register")
    public void registerWorkingRelationship(RigisterWorkingRelationshipCmpCommand command){

        rigisterWorkingRelationshipCmpCommandHandler.handle(command);
    }

    @POST
    @Path("update")
    public void updateWorkingRelationship(UpdateWorkingRelationshipCmpCommand command){

        updateWorkingRelationshipCmpCommandHandler.handle(command);
    }

}
