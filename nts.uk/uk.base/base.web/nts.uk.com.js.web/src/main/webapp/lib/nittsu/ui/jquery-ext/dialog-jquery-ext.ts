/// <reference path="../../reference.ts"/>

module nts.uk.ui.jqueryExtentions {

    module ntsDialogEx {
        $.fn.ntsDialogEx = function(action: string, winContainer): any {

            var $dialog = $(this);

            switch (action) {
                case 'centerUp':
                    centerUp($dialog, winContainer);
                default:
                    break;
            }
        };

        function centerUp($dialog: JQuery, winContainer: ScreenWindow) {
//            let currentInfo = winContainer;
            let top=0, left=0;
            let dialog = $dialog.closest("div[role='dialog']");
            dialog.addClass("disappear");
            if(winContainer.isRoot){
                //top = (window.innerHeight - dialog.innerHeight()) / 2;
                //left = (window.innerWidth - dialog.innerWidth()) / 2;
            } else {
//                while(!nts.uk.util.isNullOrUndefined(currentInfo)){
//                    if(currentInfo.isRoot){
//                        currentInfo = null;
//                    } else {
                        var offset = winContainer.$dialog.closest("div[role='dialog']").offset(); 
//                        var offset = fullDialog.offset();
                        console.log(dialog.offset());
                        top += offset.top;
                        left += offset.left;
//                        currentInfo = currentInfo.parent;
//                    }
//                }
            }
            
            setTimeout(function(){
                let dialogM = winContainer.isRoot ? $("body") : winContainer.$dialog.closest("div[role='dialog']");
                let topDiff = (dialogM.innerHeight() - dialog.innerHeight()) / 2;
                let leftDiff = (dialogM.innerWidth() - dialog.innerWidth()) / 2;
                if(topDiff > 0){
                    top += topDiff;
                }
                if(leftDiff > 0){
                    left += leftDiff;
                }
                dialog.css({top: top, left: left});
                dialog.removeClass("disappear");
            }, 33);
        }
    }
}