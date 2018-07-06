module nts.uk.com.view.cmf002.v2.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import model = cmf002.share.model;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        listOutputCodeConvert: KnockoutObservableArray<OutputCodeConvert>
        selectedOutputCodeConvert: KnockoutObservable<string>
        constructor() {
            var self = this;
            let firstItem = new OutputCodeConvert("", getText('CMF002_502'), 0);
            self.listOutputCodeConvert = ko.observableArray([firstItem]);
            self.selectedOutputCodeConvert = ko.observable("");
            self.selectedOutputCodeConvert.subscribe(x =>{
                if (x != "")  nts.uk.ui.errors.clearAll();  
            });
        }
        start(): JQueryPromise<any> {

            //Todo: getshared khi chuyen tu man hinh J sang MH v2
            //            let params = getShared("CMF001v2Params");
            //            if (params) {
            //                let cId = params.cid;
            let cId = "000000000000-0001";

            block.invisible();
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            service.getOutputCodeConvertByCid(cId).done(function(result: Array<any>) {
                if (result && result.length) {
                    let _outputCodeConverttResult: Array<any> = _.sortBy(result, ['convertCode']);
                    let _listOutputCodeConvert: Array<OutputCodeConvert> = _.map(_outputCodeConverttResult, x => {
                        return new OutputCodeConvert(x.convertCode, x.convertName, x.acceptWithoutSetting);
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
            //            }
        }
        
        selectConvertCode() {
            $('#V2_2_container').ntsError('clear');
            let self = this;
            let outputCodeConvert = new OutputCodeConvert("", "", 0);
            if (!_.isEqual(self.selectedOutputCodeConvert(), "")) {
                outputCodeConvert = _.find(ko.toJS(self.listOutputCodeConvert), (x: OutputCodeConvert) => x.dispConvertCode == self.selectedOutputCodeConvert());
                setShared("CMF002v2Params", { outputCodeConvert: outputCodeConvert });
                nts.uk.ui.windows.close();
            } else {
                $('#V2_2_container').ntsError('set', { messageId: "Msg_656" });
            }
        }

        cancelSelectConvertCode() {
            nts.uk.ui.windows.close();
        }

    }

    export class OutputCodeConvert {
        convertCode: KnockoutObservable<string>;
        convertName: KnockoutObservable<string>;
        acceptWithoutSetting: KnockoutObservable<number>;
        dispConvertCode: string;
        dispConvertName: string;

        constructor(code: string, name: string, acceptWithoutSetting: number) {
            this.convertCode = ko.observable(code);
            this.convertName = ko.observable(name);
            this.acceptWithoutSetting = ko.observable(acceptWithoutSetting);
            this.dispConvertCode = code;
            this.dispConvertName = name;
        }
    }
}