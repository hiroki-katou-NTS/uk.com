/// <reference path="../reference.ts"/>

module nts.uk.ui.action {

    module content {

        ui.documentReady.add(() => {
            $('#functions-area').addClass("disappear");
            $('#functions-area-bottom').addClass("disappear");
            $('#contents-area').addClass("disappear");
            $('#master-content').addClass("disappear");
        });

        ui.viewModelApplied.add(() => {
            $('#functions-area').removeClass("disappear");
            $('#functions-area-bottom').removeClass("disappear");
            $('#contents-area').removeClass("disappear");
            $('#master-content').removeClass("disappear");
            if($('#sidebar').length > 0){
                $('#sidebar').ntsSideBar("reactive");
            }
        });

    }
}
