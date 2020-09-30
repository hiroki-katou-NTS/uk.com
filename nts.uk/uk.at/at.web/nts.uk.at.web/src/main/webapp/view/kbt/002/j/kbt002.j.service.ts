module nts.uk.at.view.kbt002.j.service {
  const paths = {
    getAggrPeriod: "at/function/resultsperiod/getAggrPeriod",
    findAll: "at/function/resultsperiod/findAll",
    createAggrPeriod: "at/function/resultsperiod/save",
    removeAggrPeriod: "at/function/resultsperiod/removeAggrPeriod"
  }

  export function getAggrPeriod(): JQueryPromise<any> {
    return nts.uk.request.ajax("at", paths.getAggrPeriod);
  }

  export function findAll(): JQueryPromise<any[]> {
    return nts.uk.request.ajax("at", paths.findAll);
  }

  export function createAggrPeriod(data): JQueryPromise<any> {
    return nts.uk.request.ajax("at", paths.createAggrPeriod, data);
  }

  export function removeAggrPeriod(aggrFrameCode): JQueryPromise<any> {
    return nts.uk.request.ajax("at", `${paths.removeAggrPeriod}/${aggrFrameCode}`);
  }

}