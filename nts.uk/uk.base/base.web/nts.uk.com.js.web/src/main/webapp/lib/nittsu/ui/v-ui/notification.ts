module nts.uk.ui.notification {
    @component({
        name: 'ui-notification',
        template: `<svg width="35" height="30" viewBox="0 0 35 30" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path fill-rule="evenodd" clip-rule="evenodd" d="M17.9284 0.499076C17.5433 -0.16636 16.5826 -0.166358 16.1975 0.499078L0.136004 28.25C-0.249844 28.9166 0.231219 29.7509 1.00149 29.7509H33.1244C33.8947 29.7509 34.3757 28.9166 33.9899 28.25L17.9284 0.499076ZM15.5629 11.0352L15.9278 21.1757H18.1575L18.5629 11.0352H15.5629ZM15.7251 24.6568C15.7251 24.9091 15.867 25.0352 16.1507 25.0352H17.894C18.0156 25.0352 18.117 25.01 18.198 24.9595C18.2791 24.9091 18.3197 24.8082 18.3197 24.6568V22.7271C18.3197 22.4622 18.1778 22.3298 17.894 22.3298H16.1507C15.867 22.3298 15.7251 22.4622 15.7251 22.7271V24.6568Z" fill="#F18855"/>
        </svg>
        <div class="text" data-bind="i18n: $component.notification"></div>
        <span class="close" data-bind="click: $component.hide">&times;</span>
        `
    })
    export class NotificationViewModel extends ko.ViewModel {
        notification!: KnockoutObservable<string>;

        created() {
            const vm = this;

            vm.notification = nts.uk.ui._viewModel.kiban.notification;
        }

        mounted() {
            const vm = this;

            ko.computed({
                read: () => {
                    const notif = ko.unwrap<string>(vm.notification);

                    if (!notif) {
                        vm.$el.classList.add('hidden');
                    } else {
                        vm.$el.classList.remove('hidden');
                    }

                    // update position & size of all relative element
                    $(window).trigger('wd.resize');
                },
                disposeWhenNodeIsRemoved: vm.$el
            });

            // query notification via api at here
        }

        hide() {
            const vm = this;

            vm.notification('');
        }
    }
}