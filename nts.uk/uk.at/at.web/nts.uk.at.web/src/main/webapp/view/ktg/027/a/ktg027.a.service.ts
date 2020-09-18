module nts.uk.at.view.ktg027.a.service {
    import ajax = nts.uk.request.ajax;
   
    
    var paths: any = {
        getDataInit: "screen/at/overtimehours/getOvertimedDisplayForSuperiorsDto/",
        getOnChangeDate:"screen/at/overtimehours/onChangeDate/",
        getOvertimeHours: "screen/at/overtimehours/getovertimehours/",
        buttonPressingProcess : "screen/at/overtimehours/buttonPressingProcess/{0}/{1}"
    }
     /** Start page */  
    export function getDataInit(currentOrNextMonth: number): JQueryPromise<any> {
        return ajax("at", paths.getDataInit + currentOrNextMonth);
    }
    // On Change Date
    export function onChangeDate(closureId: number, targetDate: number): JQueryPromise<any> {
      return ajax("at", paths.getOnChangeDate + closureId +'/' + targetDate);
  }
    
}  