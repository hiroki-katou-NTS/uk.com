module nts.uk.at.view.kbt002.j.service {
  const paths = {
    getAggrPeriod: "at/function/resultsperiod/getAggrPeriod",
    createAggrPeriod: "at/function/resultsperiod/createAggrPeriod",
    removeAggrPeriod: "at/function/resultsperiod/removeAggrPeriod",
    checkExistAggrPeriod: "at/function/resultsperiod/checkExistAggrPeriod",
    updateAggrPeriod: "at/function/resultsperiod/updateAggrPeriod"
  }

  export function getAggrPeriod(companyId): JQueryPromise<any> {
    return nts.uk.request.ajax("at", `${paths.getAggrPeriod}/${companyId}`);
  }

  export function createAggrPeriod(data): JQueryPromise<any> {
    return nts.uk.request.ajax("at", paths.createAggrPeriod, data);
  }

  export function removeAggrPeriod(companyId, aggrFrameCode): JQueryPromise<any> {
    return nts.uk.request.ajax("at", `${paths.removeAggrPeriod}/${companyId}/${aggrFrameCode}`);
  }

  export function checkExistAggrPeriod(companyId, aggrFrameCode): JQueryPromise<any> {
    return nts.uk.request.ajax("at",`${paths.checkExistAggrPeriod}/${companyId}/${aggrFrameCode}`);
  }

  export function updateAggrPeriod(data): JQueryPromise<any[]> {
    return nts.uk.request.ajax("at", paths.updateAggrPeriod, data);
  }
  
}