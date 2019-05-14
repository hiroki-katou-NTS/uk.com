<template>
  <div class="card mb-5">
    <ul class="nav nav-tabs card-header">
      <li class="nav-item" v-for="(m, k) in ['background', 'text']" v-bind:key="k">
        <a class="nav-link" :href="'#' + m" :class="{ 'active': !k }">{{m}}</a>
      </li>
    </ul>
    <div class="tab-content">
      <div class="tab-pane" v-for="(m, k) in ['background', 'text']" v-bind:id="m" v-bind:key="k">
        <template v-if="m === 'background'">
          <div class="row">
            <div
              class="col-md-3 col-sm-4 col-6"
              v-for="(c, i) in ['red', 'pink', 'purple', 'deep-purple', 'indigo', 'blue', 'light-blue', 'cyan', 'teal', 'green', 'light-green', 'lime', 'yellow', 'amber', 'orange', 'deep-orange', 'brown', 'grey', 'blue-grey']"
              v-bind:key="i"
            >
              <div class="swatch-title" v-bind:class="'bg-' + c">
                <h3>{{c}}</h3>
              </div>
              <div
                class="color-swatch"
                v-for="(l, j) in [50, 100, 200, 300, 400, 500, 600, 700, 800, 900, 'a100', 'a200', 'a400', 'a700']"
                v-bind:key="j"
                v-bind:class="'bg-' + c + '-' + l"
              >{{'bg-' + c + '-' + l}}</div>
            </div>
          </div>
        </template>
        <template v-else>
          <div class="row">
            <div
              class="col-md-3 col-sm-4 col-6"
              v-for="(c,x) in ['red', 'pink', 'purple', 'deep-purple', 'indigo', 'blue', 'light-blue', 'cyan', 'teal', 'green', 'light-green', 'lime', 'yellow', 'amber', 'orange', 'deep-orange', 'brown', 'grey', 'blue-grey']"
              v-bind:key="x"
            >
              <div class="swatch-title text-swatch" v-bind:class="'text-' + c">
                <h3>{{c}}</h3>
              </div>
              <div
                class="color-swatch text-swatch"
                v-for="(l,z) in [50, 100, 200, 300, 400, 500, 600, 700, 800, 900, 'a100', 'a200', 'a400', 'a700']"
                v-bind:key="z"
                v-bind:class="'text-' + c + '-' + l"
              >{{'text-' + c + '-' + l}}</div>
            </div>
          </div>
        </template>
      </div>
    </div>
  </div>
</template>