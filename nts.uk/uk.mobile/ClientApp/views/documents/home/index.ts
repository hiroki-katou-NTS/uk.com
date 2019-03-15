import { Vue } from '@app/provider';
import { LanguageBar } from '@app/plugins';
import { component } from '@app/core/component';

import { NavbarComponent } from './nav';

@component({
    route: '/documents',
    template: `<div id="document_index" class="row">
        <div class="col-md-3">
            <navi-bar />
        </div>
        <router-view :class="'col-md-9'" />
    </div>`,
    style: `#document_index { margin-bottom: 15px; } @media (max-width: 768px) { #component .markdown-content {margin-top: 15px;} }`,
    components: {
        'lang-bar': LanguageBar,
        'navi-bar': NavbarComponent
    },
    resource: {
        vi: {
            documents: 'Tài liệu',
            MarkdownComponent: 'Ngôn ngữ hạ đánh dấu'            
        }
    }
})
export class DocumentIndex extends Vue { }