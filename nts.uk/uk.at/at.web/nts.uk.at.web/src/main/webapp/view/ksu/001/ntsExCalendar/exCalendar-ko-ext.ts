module nts.uk.at.view.ksu001 {

    class ExCalendarBindingHandler implements KnockoutBindingHandler {

        init(element: any, valueAccessor: () => any, allBindingsAccessor?: KnockoutAllBindingsAccessor, viewModel?: any, bindingContext?: KnockoutBindingContext): void {
            let data = valueAccessor();
            let colors: any[] = ko.unwrap(data.colors());
            let heightC: string = !!data.height ? ko.unwrap(data.height()) : 'auto';
            let widthC: string = !!data.width ? ko.unwrap(data.width()) : '400px';
            let startDate: Date = new Date(ko.unwrap(data.startDate()));
            let endDate: Date = new Date(ko.unwrap(data.endDate()));
            let startDateString: string = moment(startDate).format('YYYY/MM/DD');
            let endDateString: string = moment(endDate).format('YYYY/MM/DD');
            //if endDate is 2017/12/23,calendar will display to 2017/12/22, so add 1 days
            endDate.setDate(endDate.getDate() + 1);

            let container = $(element);
            //set width
            container.css('width', widthC);

            container.fullCalendar({
                height: heightC,
                dateAlignment: "week",
                defaultDate: startDate,
                validRange: {
                    start: startDate,
                    end: endDate
                },
                locale: "ja",
                showNonCurrentDates: true,
                header: false,
            });

            let arrayColor: any[] = _.filter(colors, ['rowId', 0]);
            if (arrayColor.length > 0) {
                //set color for header of cell, which is date of week
                $('th.fc-sun').addClass('color-sunday');
                $('th.fc-sat').addClass('color-saturday');

                //set color for header of cell, which is not disable
                _.each($('span.fc-day-number'), (y) => {
                    var element = $(y);
                    let clazz: string = _.find(arrayColor, ['columnKey', moment($(element.closest('td')).attr('data-date'), 'YYYY-MM-DD').format('_YYYYMMDD')]).clazz;
                    $(y).addClass(clazz);
                });
            } 
            
            _.each($('div.fc-content-skeleton thead td'), (x) => {
                $(x).addClass('fc-day-top color-gray');
            });
            

            //create timeLable and button
            $(container).prepend("<div id='periodCoverd' data-bind='ntsFormLabel: {}'>" + nts.uk.resource.getText("KSU001_346") + "</div>");
            $("<span id='startDate' data-bind='text: ko.observable(" + '"' + startDateString + '"' + ")'></span>").insertAfter("#periodCoverd");
            $("<span id='special'>" + nts.uk.resource.getText("KSU001_347") + "</span>").insertAfter("#startDate");
            $("<span id='endDate' data-bind='text: ko.observable(" + '"' + endDateString + '"' + ")'></span>").insertAfter("#special");
            $("<button id='checkAll'>" + nts.uk.resource.getText("KSU001_76") + "</button>").insertAfter("#endDate");
            $("<button id='unCheckAll'>" + nts.uk.resource.getText("KSU001_77") + "</button>").insertAfter("#checkAll");

            //add checkbox for cell
            let listCellAddCheckBox: any = $('div.fc-bg td:not(.fc-disabled-day)');
            for (let i = 0; i < listCellAddCheckBox.length; i++) {
                $(listCellAddCheckBox[i]).append("<div class='" + 'checkBox-calendar' + "' id='" + 'checkBox-' + i + "' data-bind='ntsCheckBox: { checked: false }'></div>");
            }

            //When click checkbox
            $(container).on("click", ".checkBox-calendar", function(event) {
                event.preventDefault();
                event.stopPropagation();
                var element = $(event.target);
                var choosenDate = element.closest('td').attr("data-date");
                if ($(this).find('input')[0].checked) {
                    _.remove(data.value(), (x) => {
                        return x == choosenDate;
                    });
                    $(this).find('input')[0].checked = false;
                } else {
                    data.value.push(choosenDate);
                    $(this).find('input')[0].checked = true;
                }
            });
            
            //When click td contain checkbox
            $(container).on("click", ".fc-day", function(event) {
                event.preventDefault();
                event.stopPropagation();
                if($($(this)[0]).hasClass('fc-disabled-day')) return;
                var element = $(event.target);
                var choosenDate = element.closest('td').attr("data-date");
                if ($(this).find('input')[0].checked) {
                    _.remove(data.value(), (x) => {
                        return x == choosenDate;
                    });
                    $(this).find('input')[0].checked = false;
                } else {
                    data.value.push(choosenDate);
                    $(this).find('input')[0].checked = true;
                }
            });

            //checkAll
            $('#checkAll').click(() => {
                data.value([]);
                _.each($('.checkBox-calendar .ntsCheckBox-label'), (x) => {
                    var element = $(x);
                    data.value.push(element.closest('td').attr("data-date"));
                    $(x)[0].childNodes[0].checked = true;
                });
            });

            //unCheckAll
            $('#unCheckAll').click(() => {
                data.value([]);
                _.each($('.checkBox-calendar .ntsCheckBox-label'), (x) => {
                    $(x)[0].childNodes[0].checked = false;
                });
            });
        }

        update(element: any, valueAccessor: () => any, allBindingsAccessor?: KnockoutAllBindingsAccessor, viewModel?: any, bindingContext?: KnockoutBindingContext): void { }
    }

    ko.bindingHandlers['ntsExCalendar'] = new ExCalendarBindingHandler();
}