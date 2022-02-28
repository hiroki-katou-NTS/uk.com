module nts.uk.at.view.ksu011.c.viewmodel {

    const API = {
        getCountItems: "at/shared/scherec/totaltimes/getallitem"
    };

    @bean()
    class Ksu011CViewModel extends ko.ViewModel {
        itemsSwap: KnockoutObservableArray<any>;
        currentCodeListSwap: KnockoutObservableArray<any>;

        headLabel: KnockoutObservable<string>;
        warningLabel: KnockoutObservable<string>;
        rightLimit: KnockoutObservable<number>;

        constructor(params: any) {
            super();
            const vm = this;
            vm.itemsSwap = ko.observableArray([]);
            vm.currentCodeListSwap = ko.observableArray([]);
            vm.headLabel = ko.observable(params.target == "personal" ? vm.$i18n("KSU011_81") : vm.$i18n("KSU011_82"));
            vm.warningLabel = ko.observable(vm.$i18n('KSU011_80', params.target == "personal" ? ["10"] : ["5"]));
            vm.rightLimit = ko.observable(params.target == "personal" ? 10 : 5);
        }

        created(params: any) {
            const vm = this;
            vm.$ajax("at", API.getCountItems).done((countItems: Array<any>) => {
                const items = (countItems || []).filter(i => i.useAtr == 1)
                    .map(i => ({
                        no: i.totalCountNo,
                        name: i.totalTimesName
                    }));
                vm.itemsSwap(_.sortBy(items, ["no"]));
                const selectedNos = params.totalNos || [];
                selectedNos.forEach((no: number) => {
                    const item = _.find(items, i => i.no == no);
                    if (item) vm.currentCodeListSwap.push(item);
                });
            });
        }

        mounted() {
            // if IE, set up down buttons height to 250, otherwise 264
            $("#up-down").height(window.document.documentMode ? 250 : 264);
            $("#up-down .upDown-container").height(window.document.documentMode ? 250 : 264);
            $("#swap-list-grid1_container").focus();
        }

        decideSelect() {
            const vm = this;
            vm.$window.close(vm.currentCodeListSwap());
        }

        cancel() {
            const vm = this;
            vm.$window.close();
        }
    }
}