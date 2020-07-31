<template>
  <div>
    <div class="accordion">
      <div class="card">
        <div class="card-header uk-bg-accordion">
          <i class="fas fa-search"></i>
          {{ "KDPS01_52" | i18n }}
        </div>
        <div class="collapse">
          <div class="card-body">
            <div class="d-flex flex-nowrap">
              <div class="col-6 px-0">
                <nts-label>{{ "KDPS01_53" | i18n }}</nts-label>
              </div>
              <div class="col-6 px-0">
                <nts-year-month
                  v-bind:showTitle="false"
                  v-model="yearMonth"
                  name="対象月"
                />
              </div>
            </div>

            <div>
              <button
                type="button"
                class="col-12 btn btn-success btn-lg btn-block"
              >
                {{ "KDPS01_54" | i18n }}
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <table class="table mt-3 table-bordered uk-table-striped">
      <thead>
        <tr>
          <th scope="col">{{ "KDPS01_61" | i18n }}</th>
          <th scope="col">{{ "KDPS01_62" | i18n }}</th>
          <th scope="col">{{ "KDPS01_63" | i18n }}</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in setting.items" v-bind:key="item.id">
          <td>{{ item.date }}</td>
          <td>{{ item.symbol }} {{ item.time }}</td>
          <td v-bind:style="{ 'text-align': item.textAlign }">
            {{ item.stampType }}
          </td>
        </tr>
      </tbody>
    </table>
    <button type="button" class="mt-3 col-12 btn btn-secondary btn-block">
      {{ "KDPS01_64" | i18n }}
    </button>
  </div>
</template>
