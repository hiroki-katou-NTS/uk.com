module nts.uk.com.view.ccg.model {
    import ntsNumber = nts.uk.ntsNumber;
    import ntsFile = nts.uk.request.file; 
    import resource = nts.uk.resource;

    const ENUM_STANDART_WIDGET: number = 0;
    const ENUM_OPTIONAL_WIDGET: number = 1;
    const ENUM_DASBOARD: number = 2;
    const ENUM_FLOWMENU: number = 3;
    const ENUM_EXTERNALURL: number = 4

    
    /** Server topPagePartDto */
    export interface TopPagePartDto {
        companyID: string;
        topPagePartID: string;
        code: string;
        name: string;
        codeName: string;
        "type": number;
        width: number;
        height: number;
    }
    
    /** Transfer data from topPage to LayoutSetting */
    export interface TransferLayoutInfo {
        parentCode: string;
        layoutID: string;
        pgType: number;
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
        companyID?: string,
        layoutID?: string;
        placementID: string;
        column: number;
        row: number;
        placementPartDto: PlacementPartDto;
    }

    /** Server PlacementPartDto */
    export interface PlacementPartDto {
        companyID?: string;
        topPagePartID?: string;
        topPageCode?: string;
        topPageName?: string;
        "type": number;
        width: number;
        height: number;
        /* External Url */
        url?: string;
        /* FlowMenu */
        fileID?: string;
        fileName?: string;
        defClassAtr?: number;
    }
    
    /** Client Placement class */
    export class Placement {
        // Required
        placementID: string;
        row: number;
        column: number;
        width: number;
        height: number;
        name: string;
        "type": number;
        // External Url info
        url: string = "";
        // TopPagePart info
        topPagePartID: string;
        topPagePart: TopPagePart;
        constructor(placementDto: PlacementDto) {
            // Placement
            this.placementID = placementDto.placementID;
            this.row = ntsNumber.getDecimal(placementDto.row, 0);
            this.column = ntsNumber.getDecimal(placementDto.column, 0);
            // Placement Part
            var placementPartDto = placementDto.placementPartDto;
            this.width = ntsNumber.getDecimal(placementPartDto.width, 0);
            this.height = ntsNumber.getDecimal(placementPartDto.height, 0);
            this.type = placementPartDto.type;
            this.topPagePartID = placementPartDto.topPagePartID;
            if (this.type == ENUM_FLOWMENU) {
                this.topPagePart = new FlowMenu(placementPartDto);
                this.name = placementPartDto.topPageName;
            } else if (this.type == ENUM_EXTERNALURL) {
                this.name = "外部URL";
                this.url = placementPartDto.url;
            }
        }
        
        buildPlacementDto(): PlacementDto {
            var placementPartDto: PlacementPartDto = {
                "type": this.type,
                width: this.width,
                height: this.height,
                url: this.url
            }
            if (this.isFlowMenu()) {
                var flowmenu: FlowMenu = <FlowMenu> this.topPagePart;
                placementPartDto.topPagePartID = flowmenu.topPagePartID(),
                placementPartDto.topPageCode = flowmenu.topPageCode(),
                placementPartDto.topPageName = flowmenu.topPageName(),
                placementPartDto.fileID = flowmenu.fileID(),
                placementPartDto.fileName = flowmenu.fileName(),
                placementPartDto.defClassAtr = flowmenu.defClassAtr()
            } else if (this.isStandardWidget()) {
                
            } else if (this.isOptionalWidget()) {
                
            } else if (this.isDashBoard()) {
                
            } 
            
            return {
                placementID: this.placementID,
                column: this.column,
                row: this.row,
                placementPartDto: placementPartDto
            }
        }
        
        isExternalUrl(): boolean {
            return this.type == ENUM_EXTERNALURL;
        }
        
        isFlowMenu(): boolean {
            return this.type == ENUM_FLOWMENU;
        }
        
        isStandardWidget(): boolean {
            return this.type == ENUM_STANDART_WIDGET;
        }
        
        isOptionalWidget(): boolean {
            return this.type == ENUM_OPTIONAL_WIDGET;
        }
        
        isDashBoard(): boolean {
            return this.type == ENUM_DASBOARD;
        }
        
    }
    
    abstract class TopPagePart {
        topPagePartID: KnockoutObservable<string>;
        topPageCode: KnockoutObservable<string>;
        topPageName: KnockoutObservable<string>;
        width: KnockoutObservable<number>;
        height: KnockoutObservable<number>;
        "type": number;
    }
        
    export class FlowMenu extends TopPagePart {
        fileID: KnockoutObservable<string>;
        fileName: KnockoutObservable<string>;
        defClassAtr: KnockoutObservable<number>;
        constructor(dto?: PlacementPartDto) {
            super();
            this.topPagePartID = ko.observable((dto && dto.topPagePartID) ? dto.topPagePartID : "");
            this.fileID = ko.observable((dto && dto.fileID) ? dto.fileID : "");
            this.fileName = ko.observable((dto && dto.fileName) ? dto.fileName : resource.getText("CCG030_25"));
            this.defClassAtr = ko.observable((dto && dto.defClassAtr) ? dto.defClassAtr : 0);
            this.topPageCode = ko.observable((dto && dto.topPageCode) ? dto.topPageCode : "");
            this.topPageName = ko.observable((dto && dto.topPageName) ? dto.topPageName : "");
            this.width = ko.observable((dto && dto.width) ? dto.width : 4);
            this.height = ko.observable((dto && dto.height) ? dto.height : 4);
            this.type = ENUM_FLOWMENU;
        }
        
        getPreviewURL(): string {
            return ntsFile.liveViewUrl(this.fileID(), "index.htm");
        }
        
    }
}