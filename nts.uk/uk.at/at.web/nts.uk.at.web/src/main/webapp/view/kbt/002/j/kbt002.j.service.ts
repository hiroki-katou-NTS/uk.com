module nts.uk.at.view.kbt002.j.service {
  const paths = {
    findAll: "at/function/resultsperiod/findAll",
    createAggrPeriod: "at/function/resultsperiod/save",
    removeAggrPeriod: "at/function/resultsperiod/removeAggrPeriod"
  }

  export function findAll(): JQueryPromise<AggrPeriodDto[]> {
    return nts.uk.request.ajax("at", paths.findAll);
  }

  export function createAggrPeriod(data: AggrPeriodCommand): JQueryPromise<any> {
    return nts.uk.request.ajax("at", paths.createAggrPeriod, data);
  }

  export function removeAggrPeriod(aggrFrameCode: string): JQueryPromise<any> {
    return nts.uk.request.ajax("at", `${paths.removeAggrPeriod}/${aggrFrameCode}`);
  }

}