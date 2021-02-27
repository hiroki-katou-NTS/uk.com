module nts.uk.at.view.kmt005.a.viewmodel {

    const API = {
        startNew: "at/request/application/timeLeave/init",
        register: "at/request/application/timeLeave/register"
    };

    @bean()
    class ViewModel extends ko.ViewModel {
        settings: KnockoutObservableArray<FrameSetting>;
        itemListCbb1: KnockoutObservableArray<any>;
        frameRange: KnockoutObservable<number>;
        displayGoback: KnockoutObservable<boolean>;

        created(params: any) {
            const self = this;
            self.displayGoback = ko.observable(params && params.fromKMT011);
            self.itemListCbb1 = ko.observableArray([
                {value: 1, name: self.$i18n("KMT005_12")},
                {value: 2, name: self.$i18n("KMT005_13")},
                {value: 3, name: self.$i18n("KMT005_14")},
                {value: 4, name: self.$i18n("KMT005_15")},
                {value: 5, name: self.$i18n("KMT005_16")}
            ]);
            self.settings = ko.observableArray([]);
            self.frameRange = ko.observable(null);
            self.getData();
        }

        mounted() {
            const self = this;
            self.frameRange.subscribe(value => {
                self.settings().forEach(setting => {
                    if (setting.frameNo > value) {
                        setting.useAtr(false);
                    } else {
                        setting.useAtr(true);
                    }
                });
            });
        }

        getData() {
            const self = this;
            self.$blockui("show");
            for (let i = 1; i <= 5; i++) {
                self.settings.push(new FrameSetting(i));
            }
            self.$blockui("hide");
        }

        register() {
            const self = this;
            self.$validate(['.nts-input']).then((valid: boolean) => {
                if (valid) {
                    const command = {
                        settings: ko.toJS(self.settings)
                    };

                    console.log(command);
                    // vm.$blockui("show").then(() => {
                    //     return vm.$ajax(API.changeAppDate, command);
                    // }).done((res: any) => {
                    //     if (res) {
                    //         vm.timeLeaveManagement(res.timeLeaveManagement);
                    //     }
                    // }).fail((error: any) => {
                    //     vm.$dialog.error(error);
                    // }).always(() => {
                    //     vm.$blockui("hide")
                    // });
                }
            });
        }

        goback() {
            const vm = this;
            vm.$jump("/view/kmt/011/a/index.xhtml", {screen: "KMT005"});
        }

    }

    class FrameSetting {
        frameNo: number;
        inputName: string;
        frameName: KnockoutObservable<string>;
        useAtr: KnockoutObservable<boolean>;

        constructor(frameNo: number, frameName?: string, useAtr?: boolean) {
            const self = this;
            self.frameNo = frameNo;
            self.inputName = nts.uk.resource.getText("KMT005_" + (6 + frameNo));
            self.frameName = ko.observable(frameName || "");
            self.useAtr = ko.observable(frameNo == 1 ? true : !!useAtr);
        }
    }

}