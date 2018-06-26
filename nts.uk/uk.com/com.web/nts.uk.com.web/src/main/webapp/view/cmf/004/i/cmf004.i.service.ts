module nts.uk.com.view.cmf004.i.service {

    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        performDataRecover: "ctx/sys/assist/datarestoration/performDataRecover"
    }
    /**
       * send for screen I
      */
    export function performDataRecover(paramRestore): JQueryPromise<any> {
        return nts.uk.request.ajax('com', paths.performDataRecover, paramRestore);
    };
}