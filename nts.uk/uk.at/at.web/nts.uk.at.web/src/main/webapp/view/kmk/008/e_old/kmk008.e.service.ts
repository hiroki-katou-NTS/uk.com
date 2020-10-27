module nts.uk.at.view.kmk008.e_old {
    export module service {
        export class Service {
            paths = {
                getList: "screen/at/kmk008/getWorkPlaceCodes/{0}",
				getWorkPlaceCodes
                getDetail: "screen/at/kmk008/d/get",
                addAgreementTimeOfWorkPlace: "at/record/agreementTimeOfWorkPlace/addAgreementTimeOfWorkPlace",
                updateAgreementTimeOfWorkplace: "at/record/agreementTimeOfWorkPlace/updateAgreementTimeOfWorkplace",
                removeAgreementTimeOfWorkplace: "at/record/agreementTimeOfWorkPlace/removeAgreementTimeOfWorkplace",
            }

            constructor() {

            }

            getList(laborSystemAtr: number): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.getList, laborSystemAtr);
                return nts.uk.request.ajax("at", _path);
            };

            getDetail(laborSystemAtr: number, workplaceId: string): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.getDetail, laborSystemAtr, workplaceId);
                return nts.uk.request.ajax("at", _path);
            };

            addAgreementTimeOfWorkPlace(UpdateInsertTimeOfWorkPlaceModel: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.addAgreementTimeOfWorkPlace, UpdateInsertTimeOfWorkPlaceModel);
            };

            updateAgreementTimeOfWorkplace(UpdateInsertTimeOfWorkPlaceModel: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.updateAgreementTimeOfWorkplace, UpdateInsertTimeOfWorkPlaceModel);
            };

            removeAgreementTimeOfWorkplace(DeleteTimeOfWorkPlaceModel: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.removeAgreementTimeOfWorkplace, DeleteTimeOfWorkPlaceModel);
            }
        }
    }
}
