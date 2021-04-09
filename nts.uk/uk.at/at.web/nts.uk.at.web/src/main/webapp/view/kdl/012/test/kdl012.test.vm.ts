/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
// import result = require("lodash/result");

module kdl012.test.viewmodel {
    import login = nts.uk.at.view.kdp004.a.service.login;
    import getShared = nts.uk.ui.windows.getShared;

    @bean()
    export class ScreenMode extends ko.ViewModel {
        roundingModes: KnockoutObservableArray<any>;
        selectedModeCode: any;
        showExpireDateOptions: KnockoutObservableArray<any>;
        selectedShowExpireDateCode: any;

        isMultiple: KnockoutObservable<boolean>;
        showExpireDate: KnockoutObservable<boolean>;
        workFrameNoSelection: KnockoutObservable<string>;
        referenceDate: KnockoutObservable<string>
        selectionCodeTxt: KnockoutObservable<string>
        selectionCodeList: KnockoutObservableArray<string>;

        currentCodeList: KnockoutObservableArray<any> = ko.observableArray([]);

        constructor() {
            super()
            let self = this;
            self.roundingModes = ko.observableArray([
                {code: '0', name: 'Single'},
                {code: '1', name: 'Multiple'}
            ]);
            self.selectedModeCode = ko.observable(0);

            self.showExpireDateOptions = ko.observableArray([
                {code: '0', name: 'Not show expireDate'},
                {code: '1', name: 'Show ExpireDate'}
            ]);
            self.selectedShowExpireDateCode = ko.observable(1);

            self.workFrameNoSelection = ko.observable("");
            self.referenceDate = ko.observable(moment.utc().format("YYYY/MM/DD"));
            self.showExpireDate = ko.observable(false);
            self.selectionCodeTxt = ko.observable("B0000000000000000001, B0000000000000000002");
            self.selectionCodeList = ko.observableArray([]);

            self.startPage();
        }

        startPage(): JQueryPromise<any> {
            let dfd = $.Deferred();
            dfd.resolve();

            return dfd.promise();
        }

        openDialog() {
            let self = this;
            nts.uk.ui.block.invisible();
            self.$validate(".nts-input").then((valid: boolean) => {
                if (!valid) {
                    return;
                }

                if (self.selectionCodeTxt() !== undefined) {
                    let temp = _.split(self.selectionCodeTxt(), ", ");
                    // If isMultiple = false
                    if (self.selectedModeCode() === 0) {
                        let tempArr: string[] = [];
                        tempArr.push(temp[0]);
                        self.selectionCodeList(tempArr);
                    }
                    else {
                        self.selectionCodeList(temp);
                    }
                }

                let request = {
                    isMultiple: self.selectedModeCode() === "1",
                    showExpireDate: self.selectedShowExpireDateCode() === "1",
                    workFrameNoSelection: self.workFrameNoSelection(),
                    referenceDate: moment(self.referenceDate()).format("YYYY/MM/DD"),
                    selectionCodeList: self.selectionCodeList()
                };
                nts.uk.ui.windows.setShared('KDL012', request);

                nts.uk.ui.windows.sub.modal("/view/kdl/012/index.xhtml", {dialogClass: "no-close"}).onClosed(() => {
                    let self = this;
                    let curentCode = getShared('currentCodeList_KDL012');

                    if (curentCode !== undefined) {
                        self.selectionCodeList(curentCode);
                        self.currentCodeList(curentCode);
                        self.selectionCodeTxt(curentCode.toString());
                        nts.uk.ui.block.clear();
                    }
                    else {
                        self.selectionCodeList = ko.observableArray([]);
                        self.selectionCodeTxt = ko.observable("");
                        self.currentCodeList = ko.observableArray([]);
                        nts.uk.ui.block.clear();
                    }
                });
            });
        }
    }
}