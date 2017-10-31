module nts.uk.at.view.kml002.c.service {
    /**
     *  Service paths
     */
    var servicePath = {
        getDailyItems: "ctx/at/schedule/budget/verticalsetting/getDailyItems"
    }
    
    /**
     *  Find data by code
     */
    export function getDailyItems(param: any): Array<BaseItemsDto> {
        var path = nts.uk.text.format(servicePath.getDailyItems);
        return nts.uk.request.ajax("at", path, param);
    }  
    
    export interface BaseItemsDto {
        id: number,
        itemId: string,
        itemName: string,
        itemType: number,
        dispOrder: number 
    }
}
