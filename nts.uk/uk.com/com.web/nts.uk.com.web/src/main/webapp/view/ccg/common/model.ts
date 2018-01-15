module nts.uk.com.view.ccg.model {
    import ntsNumber = nts.uk.ntsNumber;

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
    
    /** Server TopPagePartDto */
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
}