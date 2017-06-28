module ccg030.b.viewmodel {
    import flowmenuModel = ccg030.a.viewmodel.model;

    export class ScreenModel {
        flowmenu: KnockoutObservable<flowmenuModel.FlowMenu>;

        constructor() {
            this.flowmenu = ko.observable(null);
        }

        /** Start Page */
        startPage(): void {
            var self = this;
            var liveviewcontainer = $("#liveview");
            var flowmenu: flowmenuModel.FlowMenu = nts.uk.ui.windows.getShared("flowmenu");
             var fileID: string = nts.uk.ui.windows.getShared("fileID");
            
            
            if (flowmenu !== undefined)
                self.flowmenu(flowmenu);
            _.defer(() => { self.setupPositionAndSize(self.flowmenu()); });
            //view image
            liveviewcontainer.append($("<img/>").attr("src", nts.uk.request.resolvePath("/webapi/shr/infra/file/storage/liveview/" + fileID)));
        }

        /** Close Dialog */
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }

        /** Setup position and size for a Placement */
        private setupPositionAndSize(flowmenu: flowmenuModel.FlowMenu) {
            $("#preview-flowmenu").css({
                width: (flowmenu.widthSize() * 150) + ((flowmenu.widthSize() - 1) * 10),
                height: (flowmenu.heightSize() * 150) + ((flowmenu.heightSize() - 1) * 10)
            });
        }
    }
}