module nts.uk.at.view.kml002.a.service {
    /**
     *  Service paths
     */
    var servicePath = {
        findAllVerticalCalSet: "at/schedule/budget/verticalsetting/findAllVerticalCalSet",
        getVerticalCalSetByCode: "at/schedule/budget/verticalsetting/getVerticalCalSetByCode/{0}",
        addVerticalCalSet: "at/schedule/budget/verticalsetting/addVerticalCalSet"
    }  
    
    /**
     *  Find all data
     */
    export function findAllVerticalCalSet(): JQueryPromise<Array<VerticalSettingDto>> {
        return nts.uk.request.ajax(servicePath.findAllVerticalCalSet);
    }
    
    /**
     *  Find data by code
     */
    export function getVerticalCalSetByCode(verticalCalCd: string): JQueryPromise<VerticalSettingDto> {
        var path = nts.uk.text.format(servicePath.getVerticalCalSetByCode, verticalCalCd);
        return nts.uk.request.ajax(path);
    }  
    
    /**
     *  Add data
     */
    export function addVerticalCalSet(data: VerticalSettingDto): JQueryPromise<any> {
        return nts.uk.request.ajax(servicePath.addVerticalCalSet, data);
    }  
    
    export interface VerticalSettingDto {
        verticalCalCd: string,
        verticalCalName: string,
        unit: number,
        useAtr: number,
        assistanceTabulationAtr: number   
    }
}
