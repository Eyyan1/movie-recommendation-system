export const API_BASE_URL = import.meta.env.PUBLIC_API_BASE_URL || 'http://localhost:8080';

export const API_ENDPOINTS = {
	auth: {
		login: `${API_BASE_URL}/login`,
		register: `${API_BASE_URL}/register`
	},
	movies: `${API_BASE_URL}/movies`,
	recommendations: `${API_BASE_URL}/recommendations`,
	ratings: `${API_BASE_URL}/my-ratings`,
	adminMovies: `${API_BASE_URL}/admin/movies`
} as const;
