module nts.uk.at.view.kml002.c.service {
    /**
     *  Service paths
     */
    var servicePath = {
        getDailyItems: "ctx/at/schedule/budget/verticalsetting/getDailyItems/{0}"
    }  
    
    /**
     *  Find data by code
     */
    export function getDailyItems(attribute: number): BaseItemsDto {
        var path = nts.uk.text.format(servicePath.getDailyItems, attribute);
        return nts.uk.request.ajax("at", path);
    }  
    
    export interface BaseItemsDto {
        dailyAttItems: Array<BaseItem>,    
        scheduleItems: Array<BaseItem>,        
        externalItems: Array<BaseItem>  
    }
    
    export interface BaseItem {
        id: number,
        itemId: string,
        itemName: string,
        itemType: number,
        dispOrder: number  
    }
}
