module nts.uk.at.view.kmk008.d {
    export module service {
        export class Service {
            paths = {
                getList: "screen/at/kmk008/d/getWorkPlaceCodes/{0}",
                getDetail: "screen/at/kmk008/d/get",
                addAgreementTimeOfWorkPlace: 'monthly/estimatedtime/workplace/add',
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

            addAgreementTimeOfWorkPlace(UpdateInsertTimeOfWorkPlaceModel: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.addAgreementTimeOfWorkPlace, UpdateInsertTimeOfWorkPlaceModel);
            };

            removeAgreementTimeOfWorkplace(DeleteTimeOfWorkPlaceModel: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.removeAgreementTimeOfWorkplace, DeleteTimeOfWorkPlaceModel);
            }
        }
    }
}
