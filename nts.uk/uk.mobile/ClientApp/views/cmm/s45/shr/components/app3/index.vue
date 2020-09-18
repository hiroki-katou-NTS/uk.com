<template>
  <div class="cmms45componentsapp3">
    <!-- B5_1 -->
    <div class="card mx-n4 py-2">
      <div class="card-header">{{'KAFS08_93' | i18n}}</div>
    </div>
    <!-- B5_2 -->
    <div class="px-3 py-2">
      <span>{{'KAFS08_94' | i18n}}</span>
      <span class="pl-5">08:30</span>
    </div>
    <div class="px-3 py-2">
      <span>{{'KAFS08_95' | i18n}}</span>
      <span class="pl-5">17:30</span>
    </div>
    <!-- B10 -->
      <table class="table uk-table-striped table-bordered">
        <thead>
          <tr>
            <th class="px-1" scope="col">{{"KAFS08_97" | i18n}}</th>
            <th class="px-1" scope="col">{{"KAFS08_98" | i18n}}</th>
            <th class="px-1" scope="col">{{"KAFS08_99" | i18n}}</th>
            <th class="px-1" scope="col">{{"KAFS08_100" | i18n}}</th>
            <th class="px-1 border-right-0" scope="col">{{"KAFS08_101" | i18n}}</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(data,index) in mtable" v-bind:key="index">
            <td class="px-1">{{data.date}}</td>
            <td class="px-1">{{data.opWorkTypeName}}</td>
            <td class="px-1">{{data.opWorkTimeName}}</td>
            <td class="px-1">{{data.opLeaveTime}}</td>
            <td class="px-1">{{data.opWorkTime}}</td>
          </tr>
        </tbody>
      </table>
  </div>
</template>