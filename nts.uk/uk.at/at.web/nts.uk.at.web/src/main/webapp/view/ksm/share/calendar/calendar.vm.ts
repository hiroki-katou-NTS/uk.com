/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.ksm004.share {
    // data truyền vào từ viewmodel
    export interface CalendarParam {
        baseDate: KnockoutObservable<Date>;
        holidays: KnockoutObservableArray<Date>;
    }

    // template định nghĩa cấu trúc component calendar
    const calendarHtml = `
	<!-- ko let: { $model: ko.unwrap(displayModel) } -->
	<div class="title" data-bind="date: $model.baseDate, format: 'YYYY年M月'"></div>
	<div class="week" data-bind="foreach: $model.daysOfWeek">
		<div class="day name-day" data-bind="date: $data.date, format: 'ddd', css: { holiday: $data.holiday }"></div>
	</div>
	<div class="month" data-bind="foreach: _.chunk($model.daysOfMonth, 7), css: {isEmtyMonth: $model.isEmtyMonth }">
		<div class="week" data-bind="foreach: $data">
			<div class="day" data-bind="date: $data.date, format: 'D', css: { holiday: $data.holiday, 'out-month': $data.outMonth }"></div>
		</div>
	</div>
	<!-- /ko -->
	`;

    // component name (of calendar)
    const COMPONENT_NAME = 'calendar';

    // binding để tiện sử dụng
    @handler({
        bindingName: COMPONENT_NAME,
        validatable: true,
        virtual: false
    })
    export class CalendarBindingHandler implements KnockoutBindingHandler {
        init(element: HTMLElement, valueAccessor: () => KnockoutObservable<Date>, allBindingsAccessor: KnockoutAllBindingsAccessor, ___vm: ComponentViewModel, bindingContext: KnockoutBindingContext) {
            const name = COMPONENT_NAME;
            const baseDate = valueAccessor();
            const holidays = allBindingsAccessor.get('holidays');

            // apply component
            ko.applyBindingsToNode(element, { component: { name, params: { baseDate, holidays } } }, bindingContext);

            // thông báo cho ko không binding tiếp nữa (cụ thể là cancel holidays binding)
            return { controlsDescendantBindings: true };
        }
    }

    // định nghĩa calendar component
    @component({
        name: COMPONENT_NAME,
        template: calendarHtml
    })
    export class CalendarComponent extends ko.ViewModel {
        // model sẽ hiển thị trên view
        displayModel!: KnockoutComputed<DisplayModel>;

        created(model: CalendarParam) {
            const vm = this;

            // xử lý tính toán số ngày hiển thị dựa vào
            // baseDate và danh sách ngày nghỉ
            vm.displayModel = ko.computed(() => {
                const baseDate = ko.unwrap((model || {}).baseDate || new Date());
                const holidays = ko.unwrap((model || {}).holidays || []);
                const start = moment(baseDate).startOf('month').startOf('week');
                const isHoliday = (d: moment.Moment) => !!_.find(holidays, (h: Date) => d.isSame(h, 'day'));
                const isSameMonth = (d: moment.Moment) => d.isSame(baseDate, 'month');
                // danh sách 42 ngày hiển thị
                const daysOfMonth = _.range(0, 42, 1)
                    .map((d: number) => start.clone().add(d, 'day'))
                    .map((m: moment.Moment) => ({
                        // chuyển sang date
                        date: m.toDate(),
                        // nếu là ngày nghỉ truyề vào hoặc thứ 7, chủ nhật
                        // holiday: isSameMonth(m) ? (isHoliday(m) || [0, 6].indexOf(m.weekday()) !== -1) : false,
                        holiday: isSameMonth(m) ? isHoliday(m) : false,
                        outMonth: !isSameMonth(m)
                    }));

                // Lọc ra 7 ngày đầu tiên để hiện thị thứ
                const daysOfWeek = _.filter(daysOfMonth, (__: Date, i: number) => i < 7);

                const isEmtyMonth = _.filter(daysOfMonth, data => data.holiday).length === 0;
                return { baseDate, daysOfWeek, daysOfMonth, isEmtyMonth };
            });
        }

        mounted() {
            const vm = this;
            // add class calendar vào root element của component
            $(vm.$el).addClass('holiday-calendar');
        }
    }

    interface DisplayModel {
        baseDate: Date;
        daysOfWeek: CalendarDate[];
        daysOfMonth: CalendarDate[];
        isEmtyMonth: boolean;
    }

    interface CalendarDate {
        date: Date;
        holiday: boolean;
        outMonth: boolean;
    }
}