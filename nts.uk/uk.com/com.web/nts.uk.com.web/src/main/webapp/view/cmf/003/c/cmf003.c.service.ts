module nts.uk.com.view.cmf003.c {
  import ajax = nts.uk.request.ajax;
  import format = nts.uk.text.format;

  export module service {
    const paths = {
      initDisplay: "ctx/sys/assist/autosetting/pattern/initialDisplay",
      selectPattern: "ctx/sys/assist/autosetting/pattern/select",
      addPattern: "ctx/sys/assist/autosetting/pattern/add",
      deletePattern: "ctx/sys/assist/autosetting/pattern/delete",
    }

    export function initDisplay(): JQueryPromise<any> {
      return ajax('com', paths.initDisplay);
    }

    export function selectPattern(param: any): JQueryPromise<any> {
      return ajax('com', paths.selectPattern, param);
    }

    export function addPattern(param: any): JQueryPromise<any> {
      return ajax('com', paths.addPattern, param);
    }

    export function deletePattern(param: any): JQueryPromise<any> {
      return ajax('com', paths.deletePattern, param);
    }
  }
}