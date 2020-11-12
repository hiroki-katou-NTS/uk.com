module nts.uk.at.view.kmk008.d {
    export module service {
        export class Service {
            paths = {
                getList: "screen/at/kmk008/d/getWorkPlaceCodes/{0}",
                getDetail: "screen/at/kmk008/d/get",
                addAgreementTimeOfWorkPlace: 'monthly/estimatedtime/workplace/add',
				copySetting: 'monthly/estimatedtime/workplace/copy',
                removeAgreementTimeOfWorkplace: 'monthly/estimatedtime/workplace/delete',
            };

            constructor() {

            }

            getList(laborSystemAtr: number): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.getList, laborSystemAtr);
                return nts.uk.request.ajax("at", _path);
            };

            getDetail(laborSystemAtr: number, workplaceId: string): JQueryPromise<any> {
				return nts.uk.request.ajax("at", this.paths.getDetail, {
					laborSystemAtr: laborSystemAtr,
					workplaceId: workplaceId
				});
            };

            addAgreementTimeOfWorkPlace(updateInsertTimeOfWorkPlaceModel: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.addAgreementTimeOfWorkPlace, updateInsertTimeOfWorkPlaceModel);
            };

			copySetting(command: any): JQueryPromise<Array<any>> {
				return nts.uk.request.ajax(this.paths.copySetting, command);
			}

            removeAgreementTimeOfWorkplace(deleteTimeOfWorkPlaceModel: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.removeAgreementTimeOfWorkplace, deleteTimeOfWorkPlaceModel);
            }
        }
    }
}
