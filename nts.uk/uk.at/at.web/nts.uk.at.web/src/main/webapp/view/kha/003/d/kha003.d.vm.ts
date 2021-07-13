module nts.uk.at.kha003.d {

    const API = {
        //TODO api path
    };

    @bean()
    export class ViewModel extends ko.ViewModel {
        dateHeaders: KnockoutObservableArray<DateHeader>;
        tableDataList: KnockoutObservableArray<any>;
        c21Params: KnockoutObservable<any>;
        c31Params: KnockoutObservable<any>;
        c41Params: KnockoutObservable<any>;
        c51Params: KnockoutObservable<any>;
        c21Text: KnockoutObservable<any>;
        c31Text: KnockoutObservable<any>;
        c41Text: KnockoutObservable<any>;
        c51Text: KnockoutObservable<any>;
        dateRange: KnockoutObservable<any>;

        constructor() {
            super();
            const vm = this;
            vm.c21Params = ko.observable();
            vm.c31Params = ko.observable();
            vm.c41Params = ko.observable();
            vm.c51Params = ko.observable();
            vm.c21Text = ko.observable();
            vm.c31Text = ko.observable();
            vm.c41Text = ko.observable();
            vm.c51Text = ko.observable();
            vm.dateRange = ko.observable();
            vm.dateHeaders = ko.observableArray([]);
            vm.tableDataList =ko .observableArray([
                1,2,3,4,5,6,7,8,9,10
            ]);
        }

        created() {
            const vm = this;
            _.extend(window, {vm});
            vm.$window.storage('kha003AShareData').done((data: any) => {
                console.log('in side kha003 D:' + data)
                vm.c21Params(data.c21);
                vm.c31Params(data.c31);
                vm.c41Params(data.c41);
                vm.c51Params(data.c51);
                vm.c21Text(data.c21.name);
                vm.c31Text(data.c31.name);
                vm.c41Text(data.c41.name);
                vm.c51Text(data.c51.name);
                vm.arrangeTaskHeader(vm.c21Text());
                vm.arrangeTaskHeader(vm.c31Text());
                vm.arrangeTaskHeader(vm.c41Text());
                vm.arrangeTaskHeader(vm.c51Text());
            })
            vm.$window.storage('kha003CShareData').done((data: any) => {
                vm.dateRange(data.dateRange);
                vm.getDateRange(vm.dateRange().startDate, vm.dateRange().endDate)
            })
        }

        /**
         * function for D1_4 back to kha003 A screen
         */
        backToAScreen(){
            let vm=this;
            vm.$jump('/view/kha/003/a/index.xhtml');
        }

        /**
         * function export excell data
         */
        exportExcell(){
            let vm=this;
            alert("TODO integrate with server")
        }

        /**
         * function export csv data
         */
        exportCsv(){
            let vm=this;
            alert("TODO integrate with server")
        }


        /**
         * function for display kha003 C screen
         */
        displayKha003CScreen(){
            let vm=this;
            vm.$window.modal("/view/kha/003/c/index.xhtml").then(() => {

            });
        }

        /**
         * function for arrange task headers.
         * @param task
         */
        arrangeTaskHeader(task: any) {
            let vm = this;
            if (task) {
                vm.dateHeaders.push(
                    new DateHeader(null, null, task)
                )
            }
        }

        /**
         * function for arrange date range headers.
         * @param startDate
         * @param endDate
         */
        getDateRange(startDate: any, endDate: any, steps = 1) {
            let vm = this;
            let currentDate = new Date(startDate);
            while (currentDate <= new Date(endDate)) {
                let date = new Date(currentDate.toISOString());
                let headerText = (date.getMonth() + 1) + "月" + date.getDate() + "日";
                vm.dateHeaders.push(
                    new DateHeader(date.getMonth(), date.getDate(), headerText)
                )
                // Use UTC date to prevent problems with time zones and DST
                currentDate.setUTCDate(currentDate.getUTCDate() + steps);
            }
        }

        mounted() {
            const vm = this;
        }
    }

    class DateHeader {
        month: any;
        day: any;
        text: any

        constructor(month: any, day: any, text: any) {
            this.month = month;
            this.day = day;
            this.text = text
        }
    }
}


