import { Vue } from '@app/provider';

import { menu } from '@app/utils/menu';
import { SideMenu } from '@app/services';
import { component } from '@app/core/component';

@component({
    route: '/documents',
    template: `<div>
        <h5>{{'important_document' | i18n}}</h5>
        <div class="mb-2">
            <router-link to="/documents/filters/i18n" class="mr-2">{{'i18n' | i18n}}</router-link>|
            <router-link to="/documents/component/basic" class="ml-2 mr-2">{{'view_viewmodel' | i18n}}</router-link> |
            <router-link to="/documents/plugin/router" class="ml-2 mr-2">{{'RouterPluginDocument' | i18n}}</router-link> |
            <a class="ml-2" target="_blank" href="https://getbootstrap.com/docs/4.2/getting-started/introduction/">Bootstrap</a>
            <hr />
        </div>
        <h2 class="mb-2">{{ pgName | i18n }}</h2>
        <router-view />
    </div>`,
    style: `#document_index { margin-bottom: 15px; }`,
    resource: {
        vi: {
            documents: 'Tài liệu',
            MarkdownComponent: 'Markdown',
            'html': 'Mã HTML',
            'plugins': 'Trình cắm (Plugin)',
            'components': 'Thành phần (view)',
            'filters': 'Bộ lọc (Filter)',
            'controls': 'Các điều khiển (Control)',
            'directives': 'Các chỉ định (Directive)',
            'important_document': 'Những mục tài liệu quan trọng'
        },
        jp: {
            documents: 'Documents',
            MarkdownComponent: 'Markdown',
            'html': 'HTML',
            'plugins': ' (Plugin)',
            'components': '(Component)',
            'filters': ' (Filter)',
            'controls': ' (Control)',
            'directives': ' (Directive)',
            'important_document': 'Important documents'
        }
    }
})
export class DocumentIndex extends Vue {
    public items: Array<any> = [];

    public mounted() {
        this.items = SideMenu.items;

        SideMenu.items = menu.sample;
    }

    public destroyed() {
        SideMenu.items = this.items;
    }
}