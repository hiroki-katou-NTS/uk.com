module nts.uk.at.view.test.requestlist.service

{
	// Service paths.
	var servicePath = {
		run559 : "test-rqlist/559",
		run560 : "test-rqlist/560",
		run561 : "test-rqlist/561",
		run567 : "test-rqlist/567",
		run569 : "test-rqlist/569",
		run571 : "test-rqlist/571",
		run573 : "test-rqlist/573",
		run647 : "test-rqlist/647",
	}
	
    export function run559(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax(servicePath.run559, param);
    }
    export function run560(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax(servicePath.run560, param);
    }
    export function run561(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax(servicePath.run561, param);
    }
    export function run567(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax(servicePath.run567, param);
    }
    export function run569(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax(servicePath.run569, param);
    }
    export function run571(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax(servicePath.run571, param);
    }
    export function run573(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax(servicePath.run573, param);
    }
    export function run647(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax(servicePath.run647, param);
    }

}