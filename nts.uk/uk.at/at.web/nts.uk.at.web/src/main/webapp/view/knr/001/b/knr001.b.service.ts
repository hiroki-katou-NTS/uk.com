module nts.uk.at.view.knr001.b.service {
  import ajax = nts.uk.request.ajax;

  let paths: any = {
    getDetails: "at/ctx/scherec/dailyattdcal/declare/get-filing-settings",
    register: "at/ctx/scherec/dailyattdcal/declare/register-filing-settings"
  };

  /**
  * Get Details
  */
  export function getDetails(): JQueryPromise<any> {
    return ajax(paths.getDetails);
  }

  /**
   * Register
   */
  export function register(data: any): JQueryPromise<any> {
    return ajax(paths.register, data);
  }
}