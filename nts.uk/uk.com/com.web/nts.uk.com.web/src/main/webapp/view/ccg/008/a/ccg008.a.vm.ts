 module nts.uk.com.view.ccg008.a.viewmodel {  
    export class ScreenModel {
        tabs: KnockoutObservableArray<any>;
        selectedTab: KnockoutObservable<string>;
        topPageName: KnockoutObservable<string>;
        flowmenu: KnockoutObservable<model.Placement>;
        placements: KnockoutObservableArray<model.Placement>;
        visibleMyPage: KnockoutObservable<boolean>;
        dataSource: KnockoutObservable<model.LayoutAllDto>;
        constructor() {
            var self = this;
            self.dataSource = ko.observable(null);
            self.visibleMyPage = ko.observable(true);
            self.flowmenu = ko.observable(null);
            self.topPageName = ko.observable("");
            var title1 = nts.uk.resource.getText("CCG008_1");
            var title2 = nts.uk.resource.getText("CCG008_4");
            self.placements = ko.observableArray([]);
            self.tabs = ko.observableArray([
                {id: 'tab-1', title: nts.uk.resource.getText("CCG008_1"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-2', title: nts.uk.resource.getText("CCG008_4"), content: '.tab-content-2', enable: ko.observable(true), visible: self.visibleMyPage}
            ]);
            self.selectedTab = ko.observable('tab-1');
            self.selectedTab.subscribe(function(codeChange){
                if(codeChange=='tab-1'){//hien thi du lieu top page
                    self.showToppage(self.dataSource().topPage);
                }
                if(codeChange=='tab-2'){//hien thi du lieu my page
                 self.showMypage(self.dataSource().myPage);   
                }
            });
        }
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
//            var liveviewcontainer = $("#liveview");
//             liveviewcontainer.append($("<img/>").attr("src",nts.uk.request.resolvePath("/webapi/shr/infra/file/storage/liveview/"+'636a50eb-f1e4-4142-a9f8-0ea0d45c52cc')));
//            var code =  nts.uk.ui.windows.getShared("TopPageCode"); 
            var code = '1';
            service.getTopPageByCode(code).done((data: model.LayoutAllDto) => {
                console.log(data);
                if(data.topPage!=null && data.topPage.standardMenuUrl!=null){//hien thi standardmenu
                    nts.uk.ui.windows.sub.modeless(data.topPage.standardMenuUrl);
                }
                if(data.checkMyPage == false ){//k hien thi my page
                    self.visibleMyPage(false);
                }
                if(data.check==true){//hien thi top page truoc
                    self.selectedTab('tab-1');
                    self.showToppage(data.topPage);
                }else{//hien thi my page truoc
                    self.selectedTab('tab-2');
                    self.showMypage(data.myPage);
                }
                
                self.topPageName('flow-menu');
//                if (data !== undefined) {
//                    let listPlacement: Array<model.Placement> = _.map(data.placements, (item) => {
//                        return new model.Placement(item.placementID, item.placementPartDto.name,
//                            item.row, item.column,
//                            item.placementPartDto.width, item.placementPartDto.height, item.placementPartDto.externalUrl,
//                            item.placementPartDto.topPagePartID, item.placementPartDto.type);
//                    });
//                    if(data.flowMenu != null){
//                        _.map(data.flowMenu, (items) => {
//                            listPlacement.push( new model.Placement(items.fileID, items.fileName,
//                            items.row, items.column,
//                            items.widthSize, items.heightSize, null,
//                            items.toppagePartID, 2));
//                        });
//                    }
//                    listPlacement = _.orderBy(listPlacement, ['row', 'column'], ['asc', 'asc']);
//                    console.log(listPlacement);
//                    self.changePreviewIframe("0a03c8bc-574e-49f4-939d-56b96fb39c5e");
//                    self.setupPositionAndSizeAll();
//                    if (listPlacement !== undefined)
//                        self.placements(listPlacement);
//                    _.defer(() => { self.setupPositionAndSizeAll(); });
//                    
//                }
                dfd.resolve();
            });
            return dfd.promise();
        }

        //hien thi top page
        showToppage(data: model.LayoutForTopPageDto){
            var self = this;
                if (data != null) {
                    let listPlacement: Array<model.Placement> = _.map(data.placements, (item) => {
                        return new model.Placement(item.placementID, item.placementPartDto.name,
                            item.row, item.column,
                            item.placementPartDto.width, item.placementPartDto.height, item.placementPartDto.externalUrl,
                            item.placementPartDto.topPagePartID, item.placementPartDto.type);
                    });
                    if(data.flowMenu != null){
                        _.map(data.flowMenu, (items) => {
                            listPlacement.push( new model.Placement(items.fileID, items.fileName,
                            items.row, items.column,
                            items.widthSize, items.heightSize, null,
                            items.toppagePartID, 2));
                        });
                    }
                    listPlacement = _.orderBy(listPlacement, ['row', 'column'], ['asc', 'asc']);
                    console.log(listPlacement);
//                    self.setupPositionAndSizeAll();
                    if (listPlacement !== undefined)
                        self.placements(listPlacement);
                    console.log(listPlacement);
                    _.defer(() => { self.setupPositionAndSizeAll(); });
                    
                }
        }
        //hien thi my page
        showMypage(data: model.LayoutForMyPageDto){
            var self = this;
                if (data != null) {
                    let listPlacement: Array<model.Placement> = _.map(data.placements, (item) => {
                        return new model.Placement(item.placementID, item.placementPartDto.name,
                            item.row, item.column,
                            item.placementPartDto.width, item.placementPartDto.height, item.placementPartDto.externalUrl,
                            item.placementPartDto.topPagePartID, item.placementPartDto.type);
                    });
                    if(data.flowMenu != null){
                        _.map(data.flowMenu, (items) => {
                            listPlacement.push( new model.Placement(items.fileID, items.fileName,
                            items.row, items.column,
                            items.widthSize, items.heightSize, items.fileID,
                            items.toppagePartID, 2));
                        });
                    }
                    listPlacement = _.orderBy(listPlacement, ['row', 'column'], ['asc', 'asc']);
                    console.log(listPlacement);
//                    self.setupPositionAndSizeAll();
                    if (listPlacement !== undefined)
                        self.placements(listPlacement);
                    _.defer(() => { self.setupPositionAndSizeAll(); });
                    
                }
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
         export interface LayoutForMyPageDto{
            companyID: string;
            layoutID: string;
            pgType: number;
            flowMenu: Array<FlowMenuPlusDto>;
            placements: Array<PlacementDto>;
         }
         export interface LayoutForTopPageDto{
            companyID: string;
            layoutID: string;
            pgType: number;
            flowMenu: Array<FlowMenuPlusDto>;
            placements: Array<PlacementDto>;
            standardMenuUrl: string;
         }
         export interface FlowMenuPlusDto{
            widthSize: number;
            heightSize: number;
            toppagePartID: string;
            fileID: string;
            fileName: string;
            fileType: string;
            mimeType: string;
            originalSize: number;
            storedAt: string;
            row: number;
            column: number;
             
         }
         export interface LayoutAllDto{
            /**my page*/
            myPage: LayoutForMyPageDto;
            /**top page*/
            topPage: LayoutForTopPageDto;
            /**check xem hien thi toppage hay mypage truoc*/
            check: boolean;//check = true (hien thi top page truoc)||check = false (hien thi my page truoc)
            /**check my page co duoc hien khong*/
            checkMyPage: boolean;
     }
}