import { Vue } from '@app/provider';
import { routes } from '@app/core/routes';
import { component } from '@app/core/component';

@component({
    template: `<div id="navbar">
        <ul class="list-group">
            <li class="list-group-item"><router-link to="/documents">{{'documents' | i18n}}</router-link></li>
            <li class="list-group-item" v-for="(route, k) in routes"><router-link :to="route.path">{{route.name | i18n}}</router-link></li>
        </ul>
    </div>`
})
export class NavbarComponent extends Vue {
    get routes() {
        return (routes.filter((r: any) => r.path === '/documents')[0] || { children: [] })
            .children.map(m => ({ name: m.name, path: `/documents/${m.path}`.replace(/\/+/g, '/') }));
    }
}