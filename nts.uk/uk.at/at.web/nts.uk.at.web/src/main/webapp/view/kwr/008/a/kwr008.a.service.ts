module nts.uk.at.view.kwr008.a.service{
    import format = nts.uk.text.format;
    import share = nts.uk.at.view.kwr008.share.model;
    var paths = {
        getPageBreakSelection : "at/function/annualworkschedule/get/enum/annualworkschedule/pagebreak"    
    }
    
    export function getPageBreakSelection(): JQueryPromise<Array<share.EnumConstantDto>>{
        return nts.uk.request.ajax(paths.getPageBreakSelection);
    }
}