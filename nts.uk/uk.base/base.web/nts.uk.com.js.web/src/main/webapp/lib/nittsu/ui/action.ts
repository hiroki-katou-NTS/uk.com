/// <reference path="../reference.ts"/>

module nts.uk.ui.action {

    module content {

        ui.documentReady.add(() => {
            $('#functions-area').addClass("disappear");
            $('#functions-area-bottom').addClass("disappear");
            $('#contents-area').addClass("disappear");
        });

        ui.viewModelApplied.add(() => {
            $('#functions-area').removeClass("disappear");
            $('#functions-area-bottom').removeClass("disappear");
            $('#contents-area').removeClass("disappear");
        });
        
         ui.viewModelApplied.add(function () {
            if(!nts.uk.util.isNullOrUndefined(__viewContext.program.operationMode) 
                && (__viewContext.program.operationMode == 1 || __viewContext.program.operationMode == 2)){
                let operationInfo = $("<div>", { 'class': 'operation-info-container marquee', 'id': 'operation-info' }),
                    moving = $("<div>"), text = $("<label>"), text2 = $("<label>");
                moving.append(text).append(text2);
                operationInfo.append(moving).css({ right: ($("#manual").outerWidth() + 5) + "px" });
                text.text(__viewContext.program.operationWarning);
                text2.text(__viewContext.program.operationWarning);
                $("#pg-area").append(operationInfo);
                
                operationInfo.hover(
                    function() {
                        moving.addClass( "animate-stopping" );
                    }, function() {
                        moving.removeClass( "animate-stopping" );
                    }
                );
                
                let limit = Math.floor(0 - moving.width()), current = limit, id = setInterval(running, 50);
                moving.css({ "right": current + "px" });
                operationInfo.data("animate-id", id);
                function running() {
                    if(moving.hasClass("animate-stopping")){
                        return;
                    }
                    if (current >= 200) {
                        current = limit;
                    } else {
                        current++;
                    }
                    moving.css({ "right": current + "px" });
                }
            }
        });

    }
}
