 module nts.uk.com.view.ccg008.a.viewmodel {  
    export class ScreenModel {
        tabs: KnockoutObservableArray<any>;
        selectedTab: KnockoutObservable<string>;
        layoutId: string;
        placements: KnockoutObservableArray<model.Placement>;
        constructor() {
            var self = this;
            var title1 = nts.uk.resource.getText("CCG008_1");
            var title2 = nts.uk.resource.getText("CCG008_4");
            self.placements = ko.observableArray([]);
            self.tabs = ko.observableArray([
                {id: 'tab-1', title: title1, content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-2', title: title2, content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true)}
            ]);
            self.selectedTab = ko.observable('tab-1');
            self.changePreviewIframe("0a03c8bc-574e-49f4-939d-56b96fb39c5e");
            self.layoutId = "0a03c8bc-574e-49f4-939d-56b96fb39c5e";
        }
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            service.getMyPage("0a03c8bc-574e-49f4-939d-56b96fb39c5e").done((data: model.LayoutDto) => {
                console.log(data);
                if (data !== undefined) {
                    let listPlacement: Array<model.Placement> = _.map(data.placements, (item) => {
                        return new model.Placement(item.placementID, item.placementPartDto.name,
                            item.row, item.column,
                            item.placementPartDto.width, item.placementPartDto.height, item.placementPartDto.externalUrl,
                            item.placementPartDto.topPagePartID, item.placementPartDto.type);
                    });
                    listPlacement = _.orderBy(listPlacement, ['row', 'column'], ['asc', 'asc']);
                    self.placements(listPlacement);
                    console.log(self.placements());
                    self.changePreviewIframe("0a03c8bc-574e-49f4-939d-56b96fb39c5e");
                }
                dfd.resolve();
            });
            return dfd.promise();
        }
                
            //for frame review layout
            private changePreviewIframe(layoutID: string){
//                $("#preview-iframe-1").attr("src", "/nts.uk.com.web/view/ccg/common/previewWidget/index.xhtml?layoutid=" + layoutID);
                $("#preview-iframe-2").attr("src", "/nts.uk.com.web/view/ccg/common/previewWidget/index.xhtml?layoutid=" + layoutID);
            }
            
            //for setting dialog
            openDialog(){
                let dialogTitle = nts.uk.resource.getText("CCG008_2");
                nts.uk.ui.windows.sub.modeless("/view/ccg/008/b/index.xhtml", {title: dialogTitle});
            }
            /** Setup position and size for all Placements */
            private setupPositionAndSizeAll(): void {
                var self = this;
                _.forEach(self.placements(), (placement) => {
                    self.setupPositionAndSize(placement);
                });
            }

        /** Setup position and size for a Placement */
            private setupPositionAndSize(placement: model.Placement): void {
                var $placement = $("#" + placement.placementID);
                $placement.css({
                    top: ((placement.row - 1) * 150) + ((placement.row - 1) * 10),
                    left: ((placement.column - 1) * 150) + ((placement.column - 1) * 10),
                    width: (placement.width * 150) + ((placement.width - 1) * 10),
                    height: (placement.height * 150) + ((placement.height - 1) * 10)
                });
            }
    }
     export module model {
         /** Client Placement class */
    export class Placement {
        // Required
        placementID: string;
        name: string;
        row: number;
        column: number;
        width: number;
        height: number;
        // External Url info
        isExternalUrl: boolean;
        url: string;
        // TopPagePart info
        topPagePartID: string;
        partType: number;
        constructor(placementID: string, name: string, row: number, column: number, width: number, height: number, url?: string, topPagePartID?: string, partType?: number) {
            // Non Agruments
            this.isExternalUrl = (nts.uk.util.isNullOrEmpty(url)) ? false : true;

            this.placementID = placementID;
            this.name = (this.isExternalUrl) ? "外部URL" : name;
            this.row = ntsNumber.getDecimal(row, 0);
            this.column = ntsNumber.getDecimal(column, 0);
            this.width = ntsNumber.getDecimal(width, 0);
            this.height = ntsNumber.getDecimal(height, 0);
            this.url = url;
            this.topPagePartID = topPagePartID;
            this.partType = partType;
        }
    }    
             /** Server LayoutDto */
    export interface LayoutDto {
        companyID: string;
        layoutID: string;
        pgType: number;
        placements: Array<PlacementDto>;
    }
             /** Server PlacementDto */
    export interface PlacementDto {
        companyID: string,
        placementID: string;
        layoutID: string;
        column: number;
        row: number;
        placementPartDto: PlacementPartDto;
    }
             /** Server PlacementPartDto */
    export interface PlacementPartDto {
        companyID: string;
        width: number;
        height: number;
        topPagePartID?: string;
        code?: string;
        name?: string;
        "type"?: number;
        externalUrl?: string;
    }
     }
}