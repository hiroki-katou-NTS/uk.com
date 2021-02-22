module nts.uk.at.view.kmk013.r.viewmodel {

    @bean()
    class Kaf008AViewModel extends ko.ViewModel {

        oneDayTime: KnockoutObservable<number> = ko.observable(0);
        firstHalfTime: KnockoutObservable<number> = ko.observable(0);
        secondHalfTime: KnockoutObservable<number> = ko.observable(0);
        specifiedTimeRefItems: KnockoutObservableArray<BoxModel> = ko.observableArray([]);
        specifiedTimeRefSelected: KnockoutObservable<number> = ko.observable(0);
        personalSetRefItems: KnockoutObservableArray<BoxModel> = ko.observableArray([]);
        personvalSetRefSelected: KnockoutObservable<number> = ko.observable(0);
        enablePersonalSet: KnockoutObservable<boolean> = ko.observable(true);


        created() {

            const vm = this;
            vm.specifiedTimeRefItems = ko.observableArray([
                new BoxModel(0, vm.$i18n("KMK013_550")),
                new BoxModel(1, vm.$i18n("KMK013_551")),
                new BoxModel(2, vm.$i18n("KMK013_552"))
            ]);
            vm.personalSetRefItems = ko.observableArray([
                new BoxModel(0, vm.$i18n("KMK013_553")),
                new BoxModel(1, vm.$i18n("KMK013_554"))
            ]);

            vm.enablePersonalSet = ko.computed(() => {
                return vm.specifiedTimeRefSelected() == 2;
            })
        }

        mounted() {
            const vm = this;
            vm.init();
            $('#R1_3').focus();
        }

        init() {
            const vm = this;

            vm.$blockui("grayout");
            vm.$ajax(API.INIT).done((res: VacationAddTimeSet) => {
                if (res) {
                    vm.oneDayTime(res.oneDay);
                    vm.firstHalfTime(res.morning);
                    vm.secondHalfTime(res.afternoon);
                    vm.specifiedTimeRefSelected(res.refSet);
                    vm.personvalSetRefSelected(res.personSetRef);
                }
            }).fail((err) => {
                vm.$dialog.error(err);
            }).always(() => {
                vm.$blockui("clear");
            });
        }

        register() {

            const vm = this;

            let command = {
                oneDay: vm.oneDayTime(),
                morning: vm.firstHalfTime(),
                afternoon: vm.secondHalfTime(),
                refSet: vm.specifiedTimeRefSelected(),
                personSetRef: vm.specifiedTimeRefSelected() == 2 ? vm.personvalSetRefSelected() : null
            };

            vm.$blockui("grayout");
            vm.$ajax(API.SAVE, command).done((res) => {
                vm.$dialog.info({ messageId: "Msg_15" });
            }).fail((err) => {
                vm.$dialog.error(err);
            }).always(() => vm.$blockui("clear"));

        }

        clickDone() {
            const vm = this;

            vm.$window.close({
                // data return to parent
            });
        }

        clickCancel() {
            const vm = this;

            vm.$window.close(null);
        }

    }

    class BoxModel {
        id: number;
        name: string;
        constructor(id: number, name: string){
            var self = this;
            self.id = id;
            self.name = name;
        }
    }

    interface VacationAddTimeSet {
        oneDay: number;
        morning: number;
        afternoon: number;
        refSet: number;
        personSetRef: number;
    }

    const API = {
        INIT: "at/shared/scherec/hdset/getSetting",
        SAVE: "at/shared/scherec/hdset/save"
    }

}