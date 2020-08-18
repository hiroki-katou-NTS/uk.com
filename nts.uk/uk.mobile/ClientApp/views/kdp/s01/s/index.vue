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
                v-click:500="loadData"
              >
                {{ "KDPS01_54" | i18n }}
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="mt-2 d-flex">
      <div class="col-6 px-0">
        <nts-label>{{ "KDPS01_55" | i18n }}</nts-label>
      </div>
      <div class="col-6 px-0">
        <nts-dropdown showTitle="false" v-model="selectedValue">
          <option
            v-for="(item, k) in dropdownList"
            :value="item.code"
            v-bind:key="k"
          >
            {{ item.text | i18n }}
          </option>
        </nts-dropdown>
      </div>
    </div>

    <table v-focus class="table mt-3 table-bordered uk-table-striped">
      <thead>
        <tr>
          <th class="text-center" scope="col">{{ "KDPS01_61" | i18n }}</th>
          <th class="text-center" scope="col">{{ "KDPS01_62" | i18n }}</th>
          <th class="text-center" scope="col">{{ "KDPS01_63" | i18n }}</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in getItems" v-bind:key="item.stampStringDatetime">
          <td
            v-bind:style="{
              color: getTextColor(item)
            }"
          >
            {{ item.stampStringDatetime | date("D（ddd）") }}
          </td>
          <td>
            {{ getSymbol(item) | i18n }}
            {{ item.stampStringDatetime | date("HH:mm") }}
          </td>
          <td
            v-bind:style="{
              'text-align': getTextAlign(item)
            }"
          >
            {{ item.stampAtr }}
          </td>
        </tr>
      </tbody>
    </table>
    <button
      type="button"
      v-on:click="$close"
      class="mt-3 btn btn-secondary btn-block"
    >
      {{ "KDPS01_64" | i18n }}
    </button>
  </div>
</template>
