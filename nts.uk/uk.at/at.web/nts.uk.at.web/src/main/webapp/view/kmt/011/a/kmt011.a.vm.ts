module nts.uk.at.view.kmt011.a.viewmodel {

    @bean()
    class ViewModel extends ko.ViewModel {
        backFromScreen: string;

        created(params: any) {
            const self = this;
            if (params && params.screen) {
                self.backFromScreen = params.screen;
            }
            self.$blockui("show");
            self.$ajax("at/shared/workmanagement/operationsettings/find").fail(error => {
                self.$dialog.error(error).then(() => {
                    if (error.businessException) {
                        nts.uk.request.jumpToTopPage();
                    }
                });
            }).always(() => {
                self.$blockui("hide");
            });
        }

        mounted() {
            const self = this;
            if (self.backFromScreen) {
                switch (self.backFromScreen) {
                    case "KMT005":
                        $("#A1_5").focus();
                        break;
                    case "KMT001":
                        $("#A2_5").focus();
                        break;
                    case "KMT010":
                        $("#A3_5").focus();
                        break;
                    case "KMT009":
                        $("#A3_7").focus();
                        break;
                    case "KMT014":
                        $("#A4_5").focus();
                        break;
                    default:
                        break;
                }
            } else {
                $("#A1_5").focus();
            }
        }

        jumpToOtherPage(screen: string) {
            const self = this;
            switch (screen) {
                case "KMT005":
                    self.$jump("/view/kmt/005/a/index.xhtml");
                    break;
                case "KMT001":
                    self.$jump("/view/kmt/001/a/index.xhtml");
                    break;
                case "KMT010":
                    self.$jump("/view/kmt/010/a/index.xhtml");
                    break;
                case "KMT009":
                    self.$jump("/view/kmt/009/a/index.xhtml");
                    break;
                case "KMT014":
                    self.$jump("/view/kmt/014/a/index.xhtml");
                    break;
                default:
                    break;
            }
        }

    }

}