module nts.uk.com.view.cli002.a {
  import ajax = nts.uk.request.ajax;
  export module service {
    const servicePath = {
      findBySystem: "sys/portal/pginfomation/findBySystem",
    };

    export function findBySystem(systemType): JQueryPromise<any> {
      return ajax(servicePath.findBySystem, systemType);
    }
  }
}
