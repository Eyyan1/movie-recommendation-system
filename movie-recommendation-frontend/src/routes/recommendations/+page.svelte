<script lang="ts">
	import MovieCard from '$lib/components/MovieCard.svelte';
	import SectionHeader from '$lib/components/SectionHeader.svelte';
	import { featuredMovies } from '$lib/data/mock-movies';
</script>

<svelte:head>
	<title>Recommendations</title>
</svelte:head>

<div class="space-y-8">
	<SectionHeader
		eyebrow="Personalization"
		title="Recommendation UI ready for backend scoring"
		description="This route is reserved for personalized picks from the Spring Boot backend. The frontend structure is ready to render top recommendations, reasons, and empty states without baking logic into the client."
	/>

	<div class="grid gap-6 lg:grid-cols-[1.6fr_1fr]">
		<section class="grid gap-6 md:grid-cols-2">
			{#each featuredMovies.slice(0, 2) as movie}
				<MovieCard {movie} compact />
			{/each}
		</section>

		<aside class="rounded-[1.75rem] border border-white/10 bg-white/5 p-6">
			<p class="text-sm font-semibold uppercase tracking-[0.22em] text-amber-300">Backend Notes</p>
			<h2 class="mt-3 font-display text-3xl text-white">Planned API integration</h2>
			<ul class="mt-5 space-y-3 text-sm leading-7 text-slate-300">
				<li>Read recommendation cards from the Spring Boot recommendation endpoint</li>
				<li>Show recommendation reason badges such as preferred genres or rating trends</li>
				<li>Handle cold-start state for users with no ratings yet</li>
				<li>Surface empty and loading states cleanly</li>
			</ul>
		</aside>
	</div>
</div>
