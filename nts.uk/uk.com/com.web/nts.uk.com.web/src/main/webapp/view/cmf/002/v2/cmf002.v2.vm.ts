module nts.uk.com.view.cmf002.v2.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import model = cmf002.share.model;
    import alertError = nts.uk.ui.dialog.alertError;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        listOutputCodeConvert: KnockoutObservableArray<model.OutputCodeConvert>;
        selectedOutputCodeConvert: KnockoutObservable<string> = ko.observable("");;
        outputCodeConvert: KnockoutObservable<model.OutputCodeConvert>;
        constructor() {
            let self = this;
            let firstItem = new model.OutputCodeConvert("", getText('CMF002_502'), 0);
            self.listOutputCodeConvert = ko.observableArray([firstItem]);
            self.selectedOutputCodeConvert.subscribe(x => {
                if (x != "") nts.uk.ui.errors.clearAll();
            });
            let parameter = getShared('CMF002v2Params');
            if (parameter) {
                self.selectedOutputCodeConvert = ko.observable(parameter.outputCodeConvert.dispConvertCode);
            }
        }
        start(): JQueryPromise<any> {
            block.invisible();
            let self = this;
            let dfd = $.Deferred();
            dfd.resolve();
            service.getOutputCodeConvertByCompanyId().done(function(result: Array<any>) {
                if (result && result.length) {
                    let _outputCodeConverttResult: Array<any> = _.sortBy(result, ['convertCode']);
                    let _listOutputCodeConvert: Array<model.OutputCodeConvert> = _.map(_outputCodeConverttResult, x => {
                        return new model.OutputCodeConvert(x.convertCode, x.convertName, x.acceptWithoutSetting);
                    });
                    self.listOutputCodeConvert.push.apply(self.listOutputCodeConvert, _listOutputCodeConvert);
                }
                block.clear();
                dfd.resolve();
            }).fail(function(error) {
                alertError(error);
                block.clear();
                dfd.reject();
            });
            return dfd.promise();
        }

        selectConvertCode() {
            let self = this;
            let $itemV2_2 = $('#V2_2_container');
            $itemV2_2.ntsError('clear');
            if (!_.isEqual(self.selectedOutputCodeConvert(), "")) {
                self.outputCodeConvert = _.find(ko.toJS(self.listOutputCodeConvert), (x: model.OutputCodeConvert) => x.dispConvertCode == self.selectedOutputCodeConvert());
                setShared("CMF002v2Params", { outputCodeConvert: self.outputCodeConvert});
                nts.uk.ui.windows.close();
            } else {
                $itemV2_2.ntsError('set', { messageId: "Msg_656" });
            }
        }

        cancelSelectConvertCode() {
            nts.uk.ui.windows.close();
        }
        
        ///////test Xóa khi hoàn thành
        gotoScreenV2() {
            let self = this;
            self.outputCodeConvert = ko.observable(new model.OutputCodeConvert("006", "コンバージョン名", 0));
            setShared("CMF002v2Params", { outputCodeConvert: self.outputCodeConvert() });
            nts.uk.ui.windows.sub.modal("/view/cmf/002/v2/index.xhtml");
        }
    }
}