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
            var liveviewcontainer = $(".panel-content");
            var flowmenuDTO: flowmenuModel.FlowMenuDto = nts.uk.ui.windows.getShared("flowmenu");
            var fileID: string = nts.uk.ui.windows.getShared("fileID");

            if (flowmenuDTO !== undefined) {
                let flowMenu = new flowmenuModel.FlowMenu("", "", "", "", "未設定", 0, 4, 4);
                flowMenu.fromDTO(flowmenuDTO);
                self.flowmenu(flowMenu);
            }
            _.defer(() => { self.setupPositionAndSize(self.flowmenu()); });
            //view image
            liveviewcontainer.html($("<iframe width = '100%' height = '100%' />").attr("src", nts.uk.request.liveView(fileID) + "/packs/" + fileID + "/index.htm"));
        }


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