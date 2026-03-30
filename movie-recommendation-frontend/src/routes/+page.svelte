<script lang="ts">
	import HeroPanel from '$lib/components/HeroPanel.svelte';
	import MovieCard from '$lib/components/MovieCard.svelte';
	import SectionHeader from '$lib/components/SectionHeader.svelte';
	import StatCard from '$lib/components/StatCard.svelte';
	import { featuredMovies } from '$lib/data/mock-movies';
</script>

<svelte:head>
	<title>Movie Recommendation Frontend</title>
</svelte:head>

<div class="space-y-12">
	<HeroPanel
		eyebrow="New Frontend Repository"
		title="A cleaner movie product experience, built for a real backend."
		description="This SvelteKit + Tailwind frontend is the next phase of the movie recommendation system. It is designed to stay frontend-only for now, while giving us a strong visual shell, scalable route structure, and a clear place to connect the Spring Boot backend later."
		primaryAction={{ label: 'Browse planned pages', href: '/movies' }}
		secondaryAction={{ label: 'See API-ready structure', href: '/recommendations' }}
	/>

	<section class="grid gap-4 md:grid-cols-3">
		<StatCard label="UI Direction" value="Modern" detail="Editorial hero sections, bold posters, and high-contrast navigation." />
		<StatCard label="Integration Mode" value="API Ready" detail="Frontend-only foundation with centralized backend endpoint placeholders." />
		<StatCard label="First Phase" value="MVP Focused" detail="Shared layout, key routes, and reusable components before deep feature logic." />
	</section>

	<section class="space-y-6">
		<SectionHeader
			eyebrow="Launch Surface"
			title="Key screens to create first"
			description="The first phase focuses on the routes that shape the product: discovery, authentication, recommendations, ratings, and admin workflow placeholders."
		/>
		<div class="grid gap-6 lg:grid-cols-3">
			{#each featuredMovies as movie}
				<MovieCard {movie} />
			{/each}
		</div>
	</section>
</div>
