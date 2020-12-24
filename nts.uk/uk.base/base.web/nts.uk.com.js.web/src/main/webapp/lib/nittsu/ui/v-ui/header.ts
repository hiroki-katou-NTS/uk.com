module nts.uk.ui.header {
    @component({
        name: 'ui-header',
        template: `
        <svg class="hamberger" width="16" height="14" viewBox="0 0 16 14" fill="none" xmlns="http://www.w3.org/2000/svg">
        <rect width="16" height="2" rx="1" fill="white"/>
        <rect y="6" width="16" height="2" rx="1" fill="white"/>
        <rect y="12" width="16" height="2" rx="1" fill="white"/>
        </svg>
        <img class="favicon" src="/nts.uk.com.js.web/lib/nittsu/ui/style/images/kinjirou.png" />
        <div class="menu-groups" data-bind="foreach: ['事前設定', 'スケジュール', '実績', '申請', '補助']">
            <button class="small" data-bind="text: $data"></button>
        </div>
        <div class="user-info">
            <div class="menu-groups">
                <button class="small company" data-bind="text: '日通システム株式会社'"></button>
                <span class="divider"></span>
                <button class="small company" data-bind="text: '市村太郎'"></button>
            </div>
            <div class="avatar notification"></div>
        </div>
        `
    })
    export class HeaderViewModel extends ko.ViewModel {

        mounted() {
        }
    }
}