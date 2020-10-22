module nts.uk.at.view.kmk008.b {
	export module service {
		export class Service {
			paths: any = {
				getData: "screen/at/kmk008/b/get",
			};

			getData(laborSystemAtr: number): JQueryPromise<any> {
				return nts.uk.request.ajax(this.paths.getData, {laborSystemAtr: laborSystemAtr});
			}
		}
	}
}
