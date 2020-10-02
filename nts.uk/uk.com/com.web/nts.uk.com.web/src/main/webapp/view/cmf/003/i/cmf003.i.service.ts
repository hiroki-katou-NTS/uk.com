module nts.uk.com.view.cmf003.i.service {
  import ajax = nts.uk.request.ajax;
  var paths = {
      findSaveSetHistory: "ctx/sys/assist/datarestoration/findSaveSet",
      findData: "ctx/sys/assist/datarestoration/findData"
  }
 
  export function findSaveSetHistory(fromDate: string, toDate: string): JQueryPromise<SaveSetHistoryDto[]> {
    return ajax('com', paths.findSaveSetHistory, { from: fromDate, to: toDate });
  } 

  export function findData(param: Array<FindDataHistoryDto>): JQueryPromise<DataDto[]> {
    return ajax('com', paths.findData, { objects: param })
  }
}