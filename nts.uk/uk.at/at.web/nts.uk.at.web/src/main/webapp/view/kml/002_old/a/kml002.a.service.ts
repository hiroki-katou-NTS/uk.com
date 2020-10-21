module nts.uk.at.view.kml002.a.service {
    /**
     *  Service paths
     */
    var servicePath = {
        findAllVerticalCalSet: "ctx/at/schedule/budget/verticalsetting/findAllVerticalCalSet",
        getVerticalCalSetByCode: "ctx/at/schedule/budget/verticalsetting/getVerticalCalSetByCode/{0}",
        addVerticalCalSet: "ctx/at/schedule/budget/verticalsetting/addVerticalCalSet",
        deleteVerticalCalSet: "ctx/at/schedule/budget/verticalsetting/deleteVerticalCalSet",
        getDailyItems: "ctx/at/schedule/budget/verticalsetting/getDailyItems",
        findByAtr: "at/schedule/budget/external/findByAtr"
    }  
    
    /**
     *  Find all data
     */
    export function findAllVerticalCalSet(): JQueryPromise<Array<VerticalSettingDto>> {
        return nts.uk.request.ajax("at", servicePath.findAllVerticalCalSet);
    }
    
    /**
     *  Find data by code
     */
    export function getVerticalCalSetByCode(verticalCalCd: string): JQueryPromise<VerticalSettingDto> {
        var path = nts.uk.text.format(servicePath.getVerticalCalSetByCode, verticalCalCd);
        return nts.uk.request.ajax("at", path);
    }  
    
    /**
     *  Add data
     */
    export function addVerticalCalSet(data: VerticalSettingDto): JQueryPromise<any> {
        return nts.uk.request.ajax("at", servicePath.addVerticalCalSet, data);
    }   
    
    /**
     *  Delete data
     */
    export function deleteVerticalCalSet(verticalCalCd: string): JQueryPromise<any> {
        var path = nts.uk.text.format(servicePath.deleteVerticalCalSet);
        return nts.uk.request.ajax("at", path, { verticalCalCd: verticalCalCd });
    } 
    
    /**
     *  Get C data by code
     */
    export function getDailyItems(param: any): Array<BaseItemsDto> {
        var path = nts.uk.text.format(servicePath.getDailyItems);
        return nts.uk.request.ajax("at", path, param);
    }  
    
    /**
     *  Get D, F data by code
     */
    export function getByAtr(param: any){   
        return nts.uk.request.ajax(servicePath.findByAtr, param);   
    }
    
    export interface BaseItemsDto {
        id: number,
        itemId: string,
        itemName: string,
        itemType: number,
        dispOrder: number 
    }
    
    export interface VerticalSettingDto {
        verticalCalCd: string,
        verticalCalName: string,
        unit: number,
        useAtr: number,
        assistanceTabulationAtr: number,
        verticalCalItems: Array<VerticalCalItemDto>   
    }
    
    export interface VerticalCalItemDto {
        verticalCalCd: string,
        itemId: string,
        itemName: string,
        calculateAtr: number,
        displayAtr: number,
        cumulativeAtr: number,
        attributes: number,
        rounding: number,
        roundingProcessing: number,
        dispOrder: number
    }
}
