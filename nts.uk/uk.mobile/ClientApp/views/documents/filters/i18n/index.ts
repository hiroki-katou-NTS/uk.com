import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    route: {
        url: '/filters/home',
        parent: '/documents'
    },
    template: '<div></div>',
    markdown: {
        vi: require('./content/vi.md'),
        jp: require('./content/jp.md')
    }
})
export class FilterDocument extends Vue {
}

@component({
    route: {
        url: '/filters/i18n',
        parent: '/documents'
    },
    template: require('./index.html'),
    markdown: {
        vi: require('./content/vi.md'),
        jp: require('./content/jp.md')
    },
    resource: {
    }
})
export class I18nFilterDocument extends Vue {
}