let ko: any, $: any;

module nts.uk.pr.view.qmm005.c.viewmodel {
    export class ViewModel {
        select001: any;
        selectedCode: any;
        constructor() {
            this.selectedCode = ko.observable(1);
            this.select001 = ko.observableArray([
                new SelectItem(1, "1日"),
                new SelectItem(2, "2日"),
                new SelectItem(3, "3日"),
                new SelectItem(4, "4日"),
                new SelectItem(5, "5日"),
                new SelectItem(6, "6日"),
                new SelectItem(7, "7日"),
                new SelectItem(8, "8日"),
                new SelectItem(9, "9日"),
                new SelectItem(10, "10日"),
                new SelectItem(11, "11日"),
                new SelectItem(12, "12日"),
                new SelectItem(13, "13日"),
                new SelectItem(14, "14日"),
                new SelectItem(15, "15日"),
                new SelectItem(16, "16日"),
                new SelectItem(17, "17日"),
                new SelectItem(18, "18日"),
                new SelectItem(19, "19日"),
                new SelectItem(20, "20日"),
                new SelectItem(21, "21日"),
                new SelectItem(22, "22日"),
                new SelectItem(23, "23日"),
                new SelectItem(24, "24日"),
                new SelectItem(25, "25日"),
                new SelectItem(26, "26日"),
                new SelectItem(27, "27日"),
                new SelectItem(28, "28日"),
                new SelectItem(29, "29日"),
                new SelectItem(30, "30日"),
                new SelectItem(31, "31日"),
                ]);
        }
        
        toggleView(): void {
            $('.form-extent').toggleClass('hidden');
            if(!$('.form-extent').hasClass('hidden')){
                nts.uk.ui.windows.getSelf().setHeight(600);
                $('#contents-area').css('padding-bottom', '0');
            } else {
                nts.uk.ui.windows.getSelf().setHeight(370);
                $('#contents-area').css('padding-bottom', '');
            }
        }
    }
    
    class SelectItem {
        constructor(public index: number, public label: string) {
        }
    }
}