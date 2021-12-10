module nts.uk.at.kmr003.c {
    @bean()
    export class KMR003CViewModel extends ko.ViewModel {
        date: KnockoutObservable<string> = ko.observable(moment().format('YYYY/MM/DD'));
        receptionHour: KnockoutObservable<string> = ko.observable("");
        frameNo: any;
        empIds: any[];

        created(param: any) {
            const vm = this;

            if (param) {
                vm.date(moment(param.date).format('YYYY/MM/DD'));
                vm.receptionHour(vm.date() + " " + param.receptionHour.receptionName + " " + 
                    nts.uk.time.format.byId("Time_Short_HM", param.receptionHour.startTime) + '~' + nts.uk.time.format.byId("Time_Short_HM", param.receptionHour.endTime));
                vm.empIds = param.empIds;
                vm.frameNo = param.frameNo

                let params = {
                    correctionDate: vm.date(), 
                    frameNo: vm.frameNo, 
                    employeeIds: vm.empIds
                }

                vm.$blockui('show');
                vm.$ajax(API.startNewReservation, params).done((res: any) => {
                    if (res) {
                        console.log(res);
                        
                    }
                }).fail((error: any) => {
                    if (error) {

                    }
                }).always(() => vm.$blockui('hide'))
            }
        }

        mounted() {

        }

        register() {

        }

        close() {
            const vm = this;

            vm.$window.close();
        }
    }

    const API = {
        startNewReservation: "at/record/reservation/bento-menu/startNewReservation"
    }
}